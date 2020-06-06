package com.copy.lms.activity;

import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetCourseData;
import com.copy.lms.ResponceModel.NetCourseDataResultData;
import com.copy.lms.adapter.CourseListAdapter;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.ActivityCourselistBinding;
import com.copy.lms.db.CartDbAdapter;
import com.copy.lms.model.CourseListModel;
import com.copy.lms.model.CourseModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;
import com.copy.lms.util.Constants;
import com.copy.lms.util.CustomProgressDialog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class CourseListActivity extends BaseActivity implements View.OnClickListener {

    public static final String VALUE = "value";
    private CourseListModel model;
    private ActivityCourselistBinding binding;

    CartDbAdapter dbAdapter;
    String type = "";
    CustomProgressDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_courselist);
        model = new CourseListModel();
        model.setArrayList(new ArrayList<CourseModel>());
        binding.setCourseListModel(model);

    }

    @Override
    protected void onResume() {

        super.onResume();
        BaseAppClass.changeLang(this, BaseAppClass.getPreferences().getUserLanguageCode());
        setCounts();
    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.course));
            binding.included.toolbar.setVisibility(View.VISIBLE);
            setSupportActionBar(binding.included.toolbar);
            binding.included.imgBack.setVisibility(View.VISIBLE);
            binding.included.frameCart.setVisibility(View.VISIBLE);
            binding.included.imgSearch.setVisibility(View.GONE);
            binding.included.imgBack.setOnClickListener(this);
            binding.included.frameCart.setOnClickListener(this);

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
        dialog = new CustomProgressDialog(Constants.PROGRESS_IMAGE, CourseListActivity.this).createProgressBar();

        dbAdapter = new CartDbAdapter((CourseListActivity.this));
        if (getIntent().getStringExtra(VALUE).equalsIgnoreCase("trending")
                || getIntent().getStringExtra(VALUE).equalsIgnoreCase("featured")) {
            binding.llFilter.setVisibility(View.GONE);
        }
        binding.included.txtTitle.setText(getIntent().getStringExtra(VALUE));
        binding.included.imgBack.setOnClickListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.courseSort, R.layout.simple_spiner);
        adapter.setDropDownViewResource(R.layout.spiner_dropdown);
        binding.spinner.setAdapter(adapter);
        courseApi(getIntent().getStringExtra(VALUE), false);
        initRecycler();

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 1) {
                    type = "popular";
                    model.getArrayList().clear();
                    model.setPage(1);
                    binding.recyclerView.getAdapter().notifyDataSetChanged();
                    courseApi("popular", false);
                } else if (position == 2) {
                    type = "trending";
                    model.getArrayList().clear();
                    model.setPage(1);
                    binding.recyclerView.getAdapter().notifyDataSetChanged();
                    courseApi("trending", false);
                } else if (position == 3) {
                    type = "featured";
                    model.getArrayList().clear();
                    model.setPage(1);
                    binding.recyclerView.getAdapter().notifyDataSetChanged();
                    courseApi("featured", false);
                } else {
                    model.getArrayList().clear();
                    model.setPage(1);
                    binding.recyclerView.getAdapter().notifyDataSetChanged();
                    courseApi(type, false);
                }


                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }


    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new CourseListAdapter(CourseListActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                Intent intent = new Intent(CourseListActivity.this, CourseDetailActivity.class);
                intent.putExtra("isfree", model.getArrayList().get(position).getFree());
                intent.putExtra("id", model.getArrayList().get(position).getId() + "");
                intent.putExtra(CourseDetailActivity.COURSE_IMAGE, model.getArrayList().get(position).getImage());
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
                        courseApi(type, true);

                    }
                }
            }
        });

    }


    public void courseApi(String type, boolean isscroll) {
        if (!isscroll)
            model.getArrayList().clear();
        dialog.setCancelable(false);
        dialog.show();
        binding.progressBar.setVisibility(View.GONE);
        model.setApiCallActive(true);
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    getCourseListApi(type,
                            model.getPage(),
                            callback);
        } else {
            dialog.hide();

            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            binding.progressBar.setVisibility(View.GONE);
            dialog.hide();

            NetCourseData netCourseData = (NetCourseData) object;
            if (netCourseData.getStatus().equalsIgnoreCase("success")) {
                model.setPage(model.getPage() + 1);
                model.setApiCallActive(false);
                fillArrayList(netCourseData.getResult().getData());


            } else {
                dialog.hide();
                model.setApiCallActive(false);

//                Toast.makeText(LanguageActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            model.setApiCallActive(false);
            dialog.hide();
            binding.progressBar.setVisibility(View.GONE);


        }
    };

    /*set language list*/

    private void fillArrayList(List<NetCourseDataResultData> items) {
        binding.progressBar.setVisibility(View.GONE);
        CourseModel itemModel;
        for (int i = 0; i < items.size(); i++) {
            itemModel = new CourseModel();
            itemModel.setCategory_id(items.get(i).getCategory_id());
            itemModel.setId(items.get(i).getId());
            itemModel.setTrending(items.get(i).getTrending());
            itemModel.setTitle(items.get(i).getTitle());
            itemModel.setMeta_title(items.get(i).getMeta_title());
            itemModel.setMeta_description(items.get(i).getMeta_description());
            itemModel.setMeta_keywords(items.get(i).getMeta_keywords());
            itemModel.setCourse_image(items.get(i).getCourse_image());
            itemModel.setCreated_at(items.get(i).getCreated_at());
            itemModel.setDeleted_at(items.get(i).getDeleted_at());
            itemModel.setStart_date(items.get(i).getStart_date());
            itemModel.setSlug(items.get(i).getSlug());
            itemModel.setPopular(items.get(i).getPopular());
            itemModel.setPrice(items.get(i).getPrice());
            itemModel.setPublished(items.get(i).getPublished());
            itemModel.setFeatured(items.get(i).getFeatured());
            itemModel.setImage(items.get(i).getImage());
            itemModel.setFree(items.get(i).getFree());
            model.getArrayList().add(itemModel);


        }
        binding.recyclerView.getAdapter().notifyDataSetChanged();

        notyFyDat();
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

            case R.id.frameCart:
                startActivity(new Intent(this, CartListActivity.class));
                overridePendingTransition(R.anim.animation, R.anim.animation2);


                break;

        }
    }

    /*set cart count*/
    private void setCounts() {
        int cartCount = 0;

        try {
            dbAdapter.open();
            cartCount = dbAdapter.getCount();
            dbAdapter.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (binding.included.layoutCartCount != null) {
                if (cartCount == 0) {
                    binding.included.layoutCartCount.setVisibility(View.INVISIBLE);
                } else {
                    binding.included.layoutCartCount.setVisibility(View.VISIBLE);
                    binding.included.txtCartCount.setText(String.valueOf(cartCount));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
}
