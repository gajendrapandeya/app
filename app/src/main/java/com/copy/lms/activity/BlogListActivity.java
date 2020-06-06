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
import com.copy.lms.ResponceModel.NetBlogData;
import com.copy.lms.ResponceModel.NetBlogDataBlogData;
import com.copy.lms.adapter.BlogListAdapter;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.ActivityBloglistBinding;
import com.copy.lms.model.BlogListModel;
import com.copy.lms.model.BlogModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class BlogListActivity extends BaseActivity implements View.OnClickListener {

    public static final String VALUE = "value";
    private BlogListModel model;
    private ActivityBloglistBinding binding;


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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bloglist);
        model = new BlogListModel();
        model.setArrayList(new ArrayList<BlogModel>());
        binding.setBlogListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.blog));

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
        blogListApi();

    }


    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new BlogListAdapter(BlogListActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                Intent intent = new Intent(BlogListActivity.this, BlogDetailActivity.class);
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


    public void blogListApi() {
        binding.progressBar.setVisibility(View.VISIBLE);

        model.getArrayList().clear();
        if (AppConstant.isOnline(this)){
        RetrofitClient.getInstance().getRestOkClient().
                getBlogList("",
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

            NetBlogData messageData = (NetBlogData) object;
            if (messageData.getStatus().equalsIgnoreCase("success")) {
                fillArrayList(messageData.getBlog().getData());

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

    private void fillArrayList(List<NetBlogDataBlogData> items) {
        binding.progressBar.setVisibility(View.GONE);


        BlogModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new BlogModel();
            itemModel.setId(items.get(i).getId());
            itemModel.setAuthor(items.get(i).getAuthor());
            itemModel.setBlog_author(items.get(i).getBlog_author());
            itemModel.setBlog_category(items.get(i).getBlog_category());
            itemModel.setBlog_image(items.get(i).getBlog_image());
            itemModel.setCategory(items.get(i).getCategory());
            itemModel.setCategory_id(items.get(i).getCategory_id());
            itemModel.setImage(items.get(i).getImage());
            itemModel.setUpdated_at(items.get(i).getUpdated_at());
            itemModel.setDeleted_at(items.get(i).getDeleted_at());
            itemModel.setCreated_at(items.get(i).getCreated_at());
            itemModel.setMeta_description(items.get(i).getMeta_description());
            itemModel.setMeta_keywords(items.get(i).getMeta_keywords());
            itemModel.setMeta_title(items.get(i).getMeta_title());
            itemModel.setTitle(items.get(i).getTitle());
            itemModel.setUser_id(items.get(i).getUser_id());
            itemModel.setSlug(items.get(i).getSlug());
            itemModel.setViews(items.get(i).getViews());
            itemModel.setContent(items.get(i).getContent());

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
