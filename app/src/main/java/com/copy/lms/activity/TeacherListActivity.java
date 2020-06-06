package com.copy.lms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetTeacherData;
import com.copy.lms.ResponceModel.NetTeacherDataResultData;
import com.copy.lms.adapter.TeacherListAdapter;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.ActivityTeacherlistBinding;
import com.copy.lms.model.TeacheListModel;
import com.copy.lms.model.TeacherModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TeacherListActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    private TeacheListModel model;
    private ActivityTeacherlistBinding binding;


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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teacherlist);
        model = new TeacheListModel();
        model.setArrayList(new ArrayList<TeacherModel>());
        binding.setTeacheListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.teacher));

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
        teacherApi();
    }


    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new TeacherListAdapter(TeacherListActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                Intent intent = new Intent(TeacherListActivity.this, TeacherDetailActivity.class);
                intent.putExtra(TeacherDetailActivity.TEACHER_ID, model.getArrayList().get(position).getId());
                startActivity(intent);
                overridePendingTransition(R.anim.animation, R.anim.animation2);


            }
        }));

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if ((recyclerView.getLayoutManager().getChildCount()
                        + ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition())
                        >= recyclerView.getLayoutManager().getItemCount()) {

                    if (/*model.getCount() > model.getArrayList().size() &&*/ !model.isApiCallActive()) {

                    }
                }
            }
        });

    }


    public void teacherApi() {
        binding.progressBar.setVisibility(View.VISIBLE);
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    getTeacherListApi("popular",
                            callback);
        } else {
            binding.progressBar.setVisibility(View.GONE);
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            binding.progressBar.setVisibility(View.GONE);
            NetTeacherData teacherData = (NetTeacherData) object;
            if (teacherData.getStatus().equalsIgnoreCase("success")) {
                fillArrayList(teacherData.getResult().getData());

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

    private void fillArrayList(List<NetTeacherDataResultData> items) {
        model.getArrayList().clear();
        binding.progressBar.setVisibility(View.GONE);


        TeacherModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new TeacherModel();
            itemModel.setFull_name(items.get(i).getFull_name());
            itemModel.setId(items.get(i).getId());
            itemModel.setCreated_at(items.get(i).getCreated_at());
            itemModel.setDeleted_at(items.get(i).getDeleted_at());
            itemModel.setImage(items.get(i).getImage());
            itemModel.setWorkpos(items.get(i).getTeacher_profile().getDescription());
            itemModel.setLinkedin_link(items.get(i).getTeacher_profile().getLinkedin_link());
            itemModel.setFacebook_link(items.get(i).getTeacher_profile().getFacebook_link());
            itemModel.setTwitter_link(items.get(i).getTeacher_profile().getTwitter_link());
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


}
