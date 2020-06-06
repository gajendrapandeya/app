package com.copy.lms.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetCertificatModel;
import com.copy.lms.ResponceModel.NetCertificatModelResult;
import com.copy.lms.adapter.CertificateListAdapter;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.ActivityCertificatelistBinding;
import com.copy.lms.model.CertificateListModel;
import com.copy.lms.model.CertificateModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CertificateListActivity extends BaseActivity implements View.OnClickListener {

    public static final String VALUE = "value";
    private CertificateListModel model;
    private ActivityCertificatelistBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        BaseAppClass.changeLang(this, BaseAppClass.getPreferences().getUserLanguageCode());
        super.onResume();
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_certificatelist);
        model = new CertificateListModel();
        model.setArrayList(new ArrayList<CertificateModel>());
        binding.setCertificateListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.certificate));

            binding.included.toolbar.setVisibility(View.VISIBLE);
            setSupportActionBar(binding.included.toolbar);
            binding.included.imgBack.setVisibility(View.VISIBLE);
            binding.included.imgSearch.setVisibility(View.GONE);
            binding.included.imgBack.setOnClickListener(this);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeActivity();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initViews() {
        binding.included.txtTitle.setText(getIntent().getStringExtra(VALUE));
        binding.included.imgBack.setOnClickListener(this);
        initRecycler();
        certificateList();

    }


    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new CertificateListAdapter(CertificateListActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getArrayList().get(position).getCertificate_link()));
                startActivity(browserIntent);
//                new DownloadAttachmentTask(position).execute();

            }
        }));


    }


    public void certificateList() {
        binding.progressBar.setVisibility(View.VISIBLE);

        model.getArrayList().clear();
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    apiMyCertificate("",
                            callback);
        } else {
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            binding.progressBar.setVisibility(View.GONE);

            NetCertificatModel certificatModel = (NetCertificatModel) object;
            if (certificatModel.getStatus().equalsIgnoreCase("success")) {
                fillArrayList(certificatModel.getResult());

                notyFyDat();
            } else {

//                Toast.makeText(LanguageActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            model.setApiCallActive(false);
            binding.progressBar.setVisibility(View.GONE);


        }
    };

    /*set language list*/

    private void fillArrayList(List<NetCertificatModelResult> items) {
        binding.progressBar.setVisibility(View.GONE);


        CertificateModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new CertificateModel();
            itemModel.setId(items.get(i).getId());
            itemModel.setCertificate_link(items.get(i).getCertificate_link());
            itemModel.setCourse_id(items.get(i).getCourse_id());
            itemModel.setCreated_at(items.get(i).getCreated_at());
            itemModel.setName(items.get(i).getName());
            itemModel.setStatus(items.get(i).getStatus());
            itemModel.setUpdated_at(items.get(i).getUpdated_at());
            itemModel.setUser_id(items.get(i).getUser_id());
            itemModel.setUpdated_at(items.get(i).getUpdated_at());
            itemModel.setUrl(items.get(i).getUrl());
            model.getArrayList().add(itemModel);


        }
        binding.recyclerView.getAdapter().notifyDataSetChanged();


    }

    private void notyFyDat() {
        if (model.getArrayList().size() > 0) {
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.noData.setVisibility(View.GONE);
        } else {
            binding.recyclerView.setVisibility(View.GONE);
            binding.noData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void closeActivity() {
        AppConstant.hideKeyboard(this, binding.recyclerView);
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                closeActivity();
                break;

        }
    }

    public class DownloadAttachmentTask extends AsyncTask<String, String, String> {

        String fileName;
        int position;
        boolean flag;
        int responseCode = 0;

        public DownloadAttachmentTask(int position) {
            this.position = position;
        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;

            try {
                URL url = new URL(model.getArrayList().get(position).getCertificate_link());
                URLConnection connection = url.openConnection();
                connection.setConnectTimeout(30000);
                connection.connect();
                int fileLength = connection.getContentLength();

                InputStream input = new BufferedInputStream(url.openStream());

                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getResources().getString(R.string.app_name);

                File f = new File(path);
                if (!f.exists()) {
                    f.mkdirs();
                }
                fileName = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);

                OutputStream output = new FileOutputStream(path + "/" + fileName);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / fileLength));
                    output.write(data, 0, count);
                }
                flag = true;
                output.flush();
                output.close();
                input.close();
                responseCode = ((HttpURLConnection) connection).getResponseCode();
            } catch (Exception e) {
                e.printStackTrace();
                responseCode = 0;
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String unused) {
            if (responseCode == 200) {

                try {
                    if (fileName == null) {
                        Log.d("DownloadAttachmentTask", "NUll");
                    } else {
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                getResources().getString(R.string.app_name) + "/", fileName);
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(CertificateListActivity.this, "Unable to download file.", Toast.LENGTH_SHORT).show();

            }
        }
    }

}
