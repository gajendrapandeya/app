package com.copy.lms.activity;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetCourseSearch;
import com.copy.lms.ResponceModel.NetCourseSearchResultData;
import com.copy.lms.adapter.SearchListAdapter;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.ActivitySearchlistBinding;
import com.copy.lms.db.CartDbAdapter;
import com.copy.lms.model.SearchListModel;
import com.copy.lms.model.SearchModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchListActivity extends BaseActivity implements View.OnClickListener {

    public static final String VALUE = "value";
    public static final String SEARCH = "search";
    public static final String TYPE = "type";
    private SearchListModel model;
    private ActivitySearchlistBinding binding;

    CartDbAdapter dbAdapter;
    String type ;
    String search ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_searchlist);
        model = new SearchListModel();
        model.setArrayList(new ArrayList<SearchModel>());
        binding.setSearchListModel(model);

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
        type =getIntent().getStringExtra(TYPE);
        search =getIntent().getStringExtra(SEARCH);
        dbAdapter = new CartDbAdapter((SearchListActivity.this));

        binding.included.txtTitle.setText(getString(R.string.app_name));
        binding.included.imgBack.setOnClickListener(this);
        initRecycler();

        courseApi(search,type,false);

    }



    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new SearchListAdapter(SearchListActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                Intent intent = new Intent(SearchListActivity.this, CourseDetailActivity.class);
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
                        courseApi(search,type, true);

                    }
                }
            }
        });

    }


    public void courseApi(String search,String type, boolean isscroll) {
        if (!isscroll)
            model.getArrayList().clear();
        binding.progressBar.setVisibility(View.VISIBLE);
        model.setApiCallActive(true);
        if (AppConstant.isOnline(this)){
        RetrofitClient.getInstance().getRestOkClient().
                getCourseSearch(search,
                        type,
                        callback);
    }
        else {
        Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

    }
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            binding.progressBar.setVisibility(View.GONE);

            NetCourseSearch netCourseSearch = (NetCourseSearch) object;
            if (netCourseSearch.getStatus().equalsIgnoreCase("success")) {
                model.setPage(model.getPage() + 1);
                model.setApiCallActive(false);
                fillArrayList(netCourseSearch.getResult().getData());


            } else {
                model.setApiCallActive(false);

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

    private void fillArrayList(List<NetCourseSearchResultData> items) {
        binding.progressBar.setVisibility(View.GONE);
        SearchModel itemModel;
        for (int i = 0; i < items.size(); i++) {
            itemModel = new SearchModel();
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
