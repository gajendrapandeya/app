package com.copy.lms.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetNewsData;
import com.copy.lms.ResponceModel.NetNewsDataResultData;
import com.copy.lms.adapter.NewsListAdapter;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.ActivityNewslistBinding;
import com.copy.lms.model.NewsListModel;
import com.copy.lms.model.NewsModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class  NewsListActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    private NewsListModel model;
    private ActivityNewslistBinding binding;


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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_newslist);
        model = new NewsListModel();
        model.setArrayList(new ArrayList<NewsModel>());
        binding.setNewsListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.news));
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.courseSort, R.layout.simple_spiner);
        adapter.setDropDownViewResource(R.layout.spiner_dropdown);
        newsApi();
        initRecycler();

    }




    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new NewsListAdapter(NewsListActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                Intent intent = new Intent(NewsListActivity.this, BlogDetailActivity.class);
                intent.putExtra(BlogDetailActivity.BLOG_ID,model.getArrayList().get(position).getId()+"");
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


    public void newsApi() {
        binding.progressBar.setVisibility(View.VISIBLE);
        if (AppConstant.isOnline(this)){
        RetrofitClient.getInstance().getRestOkClient().
                getNewsList("",
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

            NetNewsData netNewsData = (NetNewsData) object;
            if (netNewsData.getStatus().equalsIgnoreCase("success")) {
                fillArrayList(netNewsData.getResult().getData());

                notyFyDat();
            } else {
//                Toast.makeText(LanguageActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            binding.progressBar.setVisibility(View.GONE);

            model.setApiCallActive(false);

        }
    };

    /*set language list*/

    private void fillArrayList(List<NetNewsDataResultData> items) {
        model.getArrayList().clear();
        binding.progressBar.setVisibility(View.GONE);


        NewsModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new NewsModel();
            itemModel.setCategory_id(items.get(i).getCategory_id());
            itemModel.setId(items.get(i).getId());
            itemModel.setTitle(items.get(i).getTitle());
            itemModel.setSlug(items.get(i).getSlug());
            itemModel.setImage(items.get(i).getImage());
            itemModel.setCategory(items.get(i).getCategory());
            itemModel.setContent(items.get(i).getContent());
            itemModel.setBlog_author(items.get(i).getBlog_author());
            itemModel.setBlog_category(items.get(i).getBlog_category());
            itemModel.setBlog_image(items.get(i).getBlog_image());
            itemModel.setAuthor(items.get(i).getAuthor());
            itemModel.setUser_id(items.get(i).getUser_id());
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
