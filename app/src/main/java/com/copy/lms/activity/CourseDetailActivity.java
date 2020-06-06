package com.copy.lms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetSingleCourseData;
import com.copy.lms.ResponceModel.NetSingleCourseDataResultCourse_timeline;
import com.copy.lms.ResponceModel.NetSuccess;
import com.copy.lms.adapter.CourseDetailAdapter;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.ActivityCoursedetailBinding;
import com.copy.lms.db.CartDbAdapter;
import com.copy.lms.model.CourseDetailListModel;
import com.copy.lms.model.CourseDetailModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;
import com.copy.lms.util.Constants;
import com.copy.lms.util.CustomProgressDialog;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CourseDetailActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    public static final String COURSE_IMAGE = "image";
    public static CourseDetailListModel model;
    private ActivityCoursedetailBinding binding;

    int freeCourse;
    String courseId;

    CartDbAdapter dbAdapter;
    CustomProgressDialog dialog;


    boolean isPurchase;
    String title;
    String img;
    String des;
    String price;
    String courseType;
    String cretaedAt;
    String updatedAt;

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coursedetail);
        model = new CourseDetailListModel();
        model.setArrayList(new ArrayList<CourseDetailModel>());
        binding.setCourseDetailListModel(model);

    }


    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.course));
            binding.included.toolbar.setVisibility(View.VISIBLE);
            setSupportActionBar(binding.included.toolbar);
            binding.included.imgBack.setVisibility(View.VISIBLE);
            binding.included.imgSearch.setVisibility(View.GONE);
            binding.included.imgBack.setOnClickListener(this);

        }
    }


    @Override
    public void initViews() {
        dialog = new CustomProgressDialog(Constants.PROGRESS_IMAGE, CourseDetailActivity.this).createProgressBar();

        dbAdapter = new CartDbAdapter(CourseDetailActivity.this);
        freeCourse = getIntent().getIntExtra("isfree", 0);
        courseId = getIntent().getStringExtra("id");
        binding.included.txtTitle.setText(getIntent().getStringExtra(VALUE));
        binding.included.imgBack.setOnClickListener(this);
        binding.addTocart.setOnClickListener(this);

        if (freeCourse == 1)
            binding.addTocart.setVisibility(View.GONE);
        else
            binding.addTocart.setVisibility(View.VISIBLE);

        if (checkIfAddedTocart()) {
            binding.addTocart.setText(getString(R.string.addedtocart));
            binding.addTocart.setBackground(getResources().getDrawable(R.drawable.transperent_border));
        }


        initRecycler();
        singleCourseDetail();
        Picasso.with(CourseDetailActivity.this)
                .load(getIntent().getStringExtra(COURSE_IMAGE))
                .into(binding.courseBg);

    }

    private boolean checkIfAddedTocart() {
        boolean ifExist = false;
        try {
            dbAdapter.open();
            ifExist = dbAdapter.checkIsExist(courseId, "course");
            dbAdapter.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ifExist;
    }

    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new CourseDetailAdapter(CourseDetailActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                if (isPurchase) {
                    if (model.getArrayList().get(position).getType().equalsIgnoreCase("test")) {
//                        if (!model.getArrayList().get(position).getCompleted()) {
                            Intent intent = new Intent(CourseDetailActivity.this, TestActivity.class);
                            intent.putExtra(TestActivity.TITLE, model.getArrayList().get(position).getTitle() + "  ");
                            intent.putExtra(TestActivity.COURSEID, courseId);
                            intent.putExtra(TestActivity.ISCOMPLATED, true);
                            intent.putExtra(TestActivity.TESTID, model.getArrayList().get(position).getId());
                            startActivity(intent);
                            overridePendingTransition(R.anim.animation, R.anim.animation2);
//                        }
//                        else {
//                            Intent intent = new Intent(CourseDetailActivity.this, TestActivity.class);
//                            intent.putExtra(TestActivity.TITLE, model.getArrayList().get(position).getTitle() + "  ");
//                            intent.putExtra(TestActivity.COURSEID, courseId);
//                            intent.putExtra(TestActivity.TESTID, model.getArrayList().get(position).getId());
//                            startActivity(intent);
//                            overridePendingTransition(R.anim.animation, R.anim.animation2);
//
//                        }
                    } else {
                        Intent intent = new Intent(CourseDetailActivity.this, LessionListActivity.class);
                        intent.putExtra(LessionListActivity.LESSION_ID, model.getArrayList().get(position).getId());
                        intent.putExtra(TestActivity.COURSEID, courseId);
                        intent.putParcelableArrayListExtra(LessionListActivity.LESSIONTIMELINE, model.getArrayList());
                        startActivity(intent);
                        overridePendingTransition(R.anim.animation, R.anim.animation2);
                    }
                } else {
                    Toast.makeText(CourseDetailActivity.this, getString(R.string.pleaseByeCourse), Toast.LENGTH_SHORT).show();
                }


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
            case R.id.addTocart:
                if (freeCourse == 1)
                    getFreeCourse();
                else
                    AddtocartLocaly();
                break;

        }
    }


    public void AddtocartLocaly() {
        try {
            dbAdapter.open();
            dbAdapter.insUpdate(courseId, title, img, "", des, "course",
                    price, "", cretaedAt, updatedAt);
            dbAdapter.close();
            if (checkIfAddedTocart())
                binding.addTocart.setText(getString(R.string.addedtocart));
            Toast.makeText(CourseDetailActivity.this, "Item Add Into Cart", Toast.LENGTH_SHORT).show();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void AddtocartApi() {
        binding.progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getInstance().getRestOkClient().
                addtocartApi("course",
                        courseId,
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetSuccess netSuccess = (NetSuccess) object;
            binding.progressBar.setVisibility(View.GONE);

            if (netSuccess.getStatus().equalsIgnoreCase("success")) {
                Toast.makeText(CourseDetailActivity.this, "Item Add Into Cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CourseDetailActivity.this, "Somthing Wrong Please Try Again", Toast.LENGTH_SHORT).show();

            }


        }

        @Override
        public void failure(RetrofitError error) {
            binding.progressBar.setVisibility(View.GONE);


        }
    };

    public void getFreeCourse() {
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    getFreeCourse(
                            courseId,
                            freecallback);
        } else {
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback freecallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetSuccess netSuccess = (NetSuccess) object;
            if (netSuccess.getStatus().equalsIgnoreCase("success")) {
                Toast.makeText(CourseDetailActivity.this, "Item Add Into Cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CourseDetailActivity.this, "Somthing Wrong Please Try Again", Toast.LENGTH_SHORT).show();

            }


        }

        @Override
        public void failure(RetrofitError error) {

        }
    };


    public void singleCourseDetail() {
        dialog.setCancelable(false);
        dialog.show();
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    getSingleCourse(courseId,
                            courseCallback);
        } else {
            dialog.hide();

            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback courseCallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            dialog.hide();

            NetSingleCourseData netSingleCourseData = (NetSingleCourseData) object;
            if (netSingleCourseData.getStatus().equalsIgnoreCase("success")) {
                binding.title.setText(netSingleCourseData.getResult().getCourse().getTitle());
                binding.descriptiom.setText(netSingleCourseData.getResult().getCourse().getDescription());
                binding.price.setText(netSingleCourseData.getResult().getCourse().getPrice() + "");

                isPurchase = netSingleCourseData.getResult().getPurchased_course();
                title = netSingleCourseData.getResult().getCourse().getTitle();
                img = netSingleCourseData.getResult().getCourse().getImage();
                des = netSingleCourseData.getResult().getCourse().getDescription();
                cretaedAt = netSingleCourseData.getResult().getCourse().getCreated_at();
                updatedAt = netSingleCourseData.getResult().getCourse().getUpdated_at();
                price = netSingleCourseData.getResult().getCourse().getPrice();

                if (isPurchase)
                    binding.addTocart.setVisibility(View.GONE);
                else {
                    binding.addTocart.setVisibility(View.VISIBLE);
                    binding.addTocart.setText(getString(R.string.addtocart));
                    binding.addTocart.setBackground(getResources().getDrawable(R.drawable.transperent_border));

                }
                if (netSingleCourseData.getResult().getCourse_timeline() !=null)
                    fillArrayList(netSingleCourseData.getResult().getCourse_timeline());
                binding.timeline.setText(getString(R.string.coursetimeline));
                binding.currancy.setText("Price : " + BaseAppClass.getPreferences().getCurrancy());


            } else {
                dialog.hide();

//                Toast.makeText(LanguageActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            dialog.hide();
            model.setApiCallActive(false);

        }
    };

    /*set language list*/

    private void fillArrayList(List<NetSingleCourseDataResultCourse_timeline> items) {
        model.getArrayList().clear();
        binding.progressBar.setVisibility(View.GONE);


        CourseDetailModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new CourseDetailModel();
            itemModel.setId(items.get(i).getId());
            itemModel.setCompleted(items.get(i).getCompleted());
            itemModel.setTitle(items.get(i).getTitle());
            itemModel.setTitle(items.get(i).getTitle());
            itemModel.setDescription(items.get(i).getDescription());
            itemModel.setType(items.get(i).getType());
            model.getArrayList().add(itemModel);


        }
        binding.recyclerView.getAdapter().notifyDataSetChanged();
    }


}
