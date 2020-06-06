package com.copy.lms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetBundleDetailData;
import com.copy.lms.ResponceModel.NetBundleDetailDataResultBundleCourses;
import com.copy.lms.ResponceModel.NetSuccess;
import com.copy.lms.adapter.BundleDetailAdapter;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.ActivityBundledetailBinding;
import com.copy.lms.db.CartDbAdapter;
import com.copy.lms.model.BundleDetailListModel;
import com.copy.lms.model.BundleDetailModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;
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

public class BundleDetailActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    public static final String COURSE_IMAGE = "image";
    public static BundleDetailListModel model;
    private ActivityBundledetailBinding binding;

    int freeCourse;
    String bundleId;

    CartDbAdapter dbAdapter;

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bundledetail);
        model = new BundleDetailListModel();
        model.setArrayList(new ArrayList<BundleDetailModel>());
        binding.setBundleDetailListModel(model);

    }


    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.bundle));
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
        dbAdapter = new CartDbAdapter(BundleDetailActivity.this);
        freeCourse = getIntent().getIntExtra("isfree", 0);
        bundleId = getIntent().getStringExtra("id");
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
        singleBundleDetail();
        Picasso.with(BundleDetailActivity.this)
                .load(getIntent().getStringExtra(COURSE_IMAGE))
                .into(binding.courseBg);

    }

    private boolean checkIfAddedTocart() {
        boolean ifExist = false;
        try {
            dbAdapter.open();
            ifExist = dbAdapter.checkIsExist(bundleId,"bundle");
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
        binding.recyclerView.setAdapter(new BundleDetailAdapter(BundleDetailActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {

                Intent intent = new Intent(BundleDetailActivity.this, CourseDetailActivity.class);
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
            dbAdapter.insUpdate(bundleId, title, img, "", des, "bundle",
                    price, "", cretaedAt, updatedAt);
            dbAdapter.close();
            if (checkIfAddedTocart())
                binding.addTocart.setText(getString(R.string.addedtocart));
            Toast.makeText(BundleDetailActivity.this, "Item Add Into Cart", Toast.LENGTH_SHORT).show();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void AddtocartApi() {
        binding.progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getInstance().getRestOkClient().
                addtocartApi("course",
                        bundleId,
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetSuccess netSuccess = (NetSuccess) object;
            binding.progressBar.setVisibility(View.GONE);

            if (netSuccess.getStatus().equalsIgnoreCase("success")) {
                Toast.makeText(BundleDetailActivity.this, "Item Add Into Cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BundleDetailActivity.this, "Somthing Wrong Please Try Again", Toast.LENGTH_SHORT).show();

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
                            bundleId,
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
                Toast.makeText(BundleDetailActivity.this, "Item Add Into Cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BundleDetailActivity.this, "Somthing Wrong Please Try Again", Toast.LENGTH_SHORT).show();

            }


        }

        @Override
        public void failure(RetrofitError error) {

        }
    };


    public void singleBundleDetail() {
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    getSingleBundle(bundleId,
                            courseCallback);
        } else {
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback courseCallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetBundleDetailData netBundleDetailData = (NetBundleDetailData) object;
            if (netBundleDetailData.getStatus().equalsIgnoreCase("success")) {
                binding.title.setText(netBundleDetailData.getResult().getBundle().getTitle());
                binding.descriptiom.setText(netBundleDetailData.getResult().getBundle().getDescription());
                binding.price.setText(netBundleDetailData.getResult().getBundle().getPrice() + "");

                isPurchase = netBundleDetailData.isPurchased_bundle();
                title = netBundleDetailData.getResult().getBundle().getTitle();
                img = netBundleDetailData.getResult().getBundle().getImage();
                des = netBundleDetailData.getResult().getBundle().getDescription();
                cretaedAt = netBundleDetailData.getResult().getBundle().getCreated_at();
                updatedAt = netBundleDetailData.getResult().getBundle().getUpdated_at();
                price = netBundleDetailData.getResult().getBundle().getPrice();

                if (isPurchase)
                    binding.addTocart.setVisibility(View.GONE);
                else {
                    binding.addTocart.setVisibility(View.VISIBLE);
                    binding.addTocart.setText(getString(R.string.addtocart));
                    binding.addTocart.setBackground(getResources().getDrawable(R.drawable.transperent_border));

                }
                if (netBundleDetailData.getResult().getBundle().getCourses() !=null)
                    fillArrayList(netBundleDetailData.getResult().getBundle().getCourses());
                binding.timeline.setText(getString(R.string.coursetimeline));
                binding.currancy.setText("Price : " + BaseAppClass.getPreferences().getCurrancy());



            } else {
//                Toast.makeText(LanguageActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            model.setApiCallActive(false);

        }
    };


    private void fillArrayList(List<NetBundleDetailDataResultBundleCourses> items) {
        model.getArrayList().clear();
        binding.progressBar.setVisibility(View.GONE);

        BundleDetailModel itemModel;
        for (int i = 0; i < items.size(); i++) {
            itemModel = new BundleDetailModel();
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

    }


}
