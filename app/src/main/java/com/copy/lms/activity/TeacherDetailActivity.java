package com.copy.lms.activity;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetTeacherDetailData;
import com.copy.lms.ResponceModel.NetTeacherDetailDataResultCourses;
import com.copy.lms.adapter.TeacherSpecialistAdapter;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.ActivityTeacherDetailBinding;
import com.copy.lms.model.TeacherSpeciaListModel;
import com.copy.lms.model.TeacherSpecialModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;
import com.copy.lms.util.Constants;
import com.copy.lms.util.CustomProgressDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class TeacherDetailActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    private TeacherSpeciaListModel model;
    private ActivityTeacherDetailBinding binding;

    public static final String TEACHER_ID = "id";
    String teacherId;


    CustomProgressDialog dialog;


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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teacher_detail);
        model = new TeacherSpeciaListModel();
        model.setArrayList(new ArrayList<TeacherSpecialModel>());
        binding.setTeacherSpeciaListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.teachers));

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
        dialog = new CustomProgressDialog(Constants.PROGRESS_IMAGE, TeacherDetailActivity.this).createProgressBar();
        teacherId = getIntent().getStringExtra(TEACHER_ID);
        binding.included.txtTitle.setText(getIntent().getStringExtra(VALUE));
        binding.included.imgBack.setOnClickListener(this);
        initRecycler();
        getTeacherDetailApi();

    }


    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setNestedScrollingEnabled(false);


        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.setAdapter(new TeacherSpecialistAdapter(TeacherDetailActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {


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


    public void getTeacherDetailApi() {
        dialog.setCancelable(false);
        dialog.show();
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    getTeacherDetail(
                            "2",
                            callback);
        } else {
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            dialog.hide();

            NetTeacherDetailData netTeacherDetailData = (NetTeacherDetailData) object;
            if (netTeacherDetailData.getStatus().equalsIgnoreCase("success")) {
                binding.fullName.setText(netTeacherDetailData.getResult().getTeacher().getFull_name());

//                if (!netTeacherDetailData.getResult().getTeacher().getAddress().equalsIgnoreCase(""))
                    binding.txtAddress.setText(netTeacherDetailData.getResult().getTeacher().getAddress());
//                else
//                    binding.llAddress.setVisibility(View.GONE);
//                if (!netTeacherDetailData.getResult().getTeacher().getPhone().equalsIgnoreCase(""))
                    binding.txtNum.setText(netTeacherDetailData.getResult().getTeacher().getPhone());
//                else
//                    binding.llPhone.setVisibility(View.GONE);
//                if (!netTeacherDetailData.getResult().getTeacher().getEmail().equalsIgnoreCase(""))
                    binding.txtEmail.setText(netTeacherDetailData.getResult().getTeacher().getEmail());
//                else
//                    binding.llEmail.setVisibility(View.GONE);

                Picasso.with(TeacherDetailActivity.this)
                        .load(netTeacherDetailData.getResult().getTeacher().getImage())
                        .into(binding.teacherImage);
                fillArrayList(netTeacherDetailData.getResult().getCourses());
            } else {

            }


        }

        @Override
        public void failure(RetrofitError error) {
            dialog.hide();

        }
    };

    private void fillArrayList(List<NetTeacherDetailDataResultCourses> items) {
        model.getArrayList().clear();


        TeacherSpecialModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new TeacherSpecialModel();
            itemModel.setCourse_image(items.get(i).getCourse_image());
            itemModel.setId(items.get(i).getCategory_id());
            itemModel.setCreated_at(items.get(i).getCreated_at());
            itemModel.setDeleted_at(items.get(i).getDeleted_at());
            itemModel.setId(items.get(i).getId());
            itemModel.setImage(items.get(i).getImage());
            itemModel.setTitle(items.get(i).getTitle());
            itemModel.setDescription(items.get(i).getDescription());
            model.getArrayList().add(itemModel);


        }
        binding.recyclerView.getAdapter().notifyDataSetChanged();


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
