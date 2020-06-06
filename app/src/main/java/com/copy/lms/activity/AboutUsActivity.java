package com.copy.lms.activity;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetAboutData;
import com.copy.lms.ResponceModel.NetSponserData;
import com.copy.lms.ResponceModel.NetSponserDataResultData;
import com.copy.lms.adapter.SponserListAdapter;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.ActivityAboutusBinding;
import com.copy.lms.model.SponserListModel;
import com.copy.lms.model.SponserModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AboutUsActivity extends BaseActivity implements View.OnClickListener {
    private SponserListModel model;
    private ActivityAboutusBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        BaseAppClass.changeLang(this, BaseAppClass.getPreferences().getUserLanguageCode());
        super.onResume();
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_aboutus);
        model = new SponserListModel();
        model.setArrayList(new ArrayList<SponserModel>());
        binding.setSponserListModel(model);

    }

    @Override
    public void setToolBar() {
        binding.included.txtTitle.setText(getString(R.string.about));
        binding.included.imgBack.setOnClickListener(this);
    }

    @Override
    public void initViews() {
        aboutDataAPI();
        initRecycler();

        sponsorListApi();
    }

    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.setAdapter(new SponserListAdapter(AboutUsActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {


            }
        }));


    }

    //     Login Api Codeall
    public void aboutDataAPI() {
        binding.progressBar.setVisibility(View.VISIBLE);
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    getAboutUs("about-us",
                            forgotcallback);
        } else {
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback forgotcallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetAboutData netAboutData = (NetAboutData) object;
            binding.progressBar.setVisibility(View.GONE);

            if (netAboutData != null) {
                binding.txtTitle.setText(netAboutData.getResult().getTitle());
                binding.txtDes.setText(Html.fromHtml(netAboutData.getResult().getContent()));

            } else {
                Toast.makeText(AboutUsActivity.this, getString(R.string.somthingwrong), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            binding.progressBar.setVisibility(View.GONE);
            Toast.makeText(AboutUsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                closeActivity();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }

    public void sponsorListApi() {

        binding.progressBar.setVisibility(View.VISIBLE);
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    getSponserList("",
                            callback);
        } else {
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            binding.progressBar.setVisibility(View.GONE);

            NetSponserData netSponserData = (NetSponserData) object;
            if (netSponserData.getStatus().equalsIgnoreCase("success")) {
                fillArrayList(netSponserData.getResult().getData());

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

    private void fillArrayList(List<NetSponserDataResultData> items) {
        model.getArrayList().clear();
        SponserModel itemModel;

        for (int i = 0; i < items.size(); i++) {
            itemModel = new SponserModel();
            itemModel.setName(items.get(i).getName());
            itemModel.setId(items.get(i).getId());
            itemModel.setCreated_at(items.get(i).getCreated_at());
            itemModel.setLink(items.get(i).getLink());
            itemModel.setLogo(items.get(i).getLogo());
            itemModel.setStatus(items.get(i).getStatus());
            itemModel.setUpdated_at(items.get(i).getUpdated_at());
            itemModel.setImage(items.get(i).getImage());
            model.getArrayList().add(itemModel);


        }
        binding.recyclerView.getAdapter().notifyDataSetChanged();


    }
}
