package com.copy.lms.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetForumDataResultDiscussionsDataPost;
import com.copy.lms.adapter.ForumDetailAdapter;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.ActivityForumdetailBinding;
import com.copy.lms.model.ForumDetailListModel;
import com.copy.lms.model.ForumDetailModel;
import com.copy.lms.model.ForumModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ForumDetailActivity extends BaseActivity implements View.OnClickListener {

    public static final String VALUE = "value";
    private ForumDetailListModel model;
    private ActivityForumdetailBinding binding;

    public static final String FORUMDATA = "forumData";
    public static final String POSTDATA = "postData";
    public static final String ID = "id";
    public static final String HEADER = "header";
    public static final String DES = "des";
    public static final String COUNT = "count";
    public static final String POS = "pos";

    int pos;

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forumdetail);
        model = new ForumDetailListModel();
        model.setArrayList(new ArrayList<ForumDetailModel>());
        binding.setForumDetailListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.forums));

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

        pos = getIntent().getIntExtra(POS, 0);
        binding.included.txtTitle.setText(getIntent().getStringExtra(VALUE));
        binding.txtHeader.setText(getIntent().getStringExtra(HEADER));
        binding.txtDes.setText(getIntent().getStringExtra(DES));
        binding.included.imgBack.setOnClickListener(this);

        ArrayList<ForumModel> models = getIntent().getParcelableArrayListExtra(FORUMDATA);
        ArrayList<NetForumDataResultDiscussionsDataPost> postModel = getIntent().getParcelableArrayListExtra(POSTDATA);
        if (models != null) {
            model.setForumModelArrayList(models);
        } else {
            model.setForumModelArrayList(new ArrayList<ForumModel>());
        }
        if (postModel != null) {
            model.setPostArrayList(postModel);
        } else {
            model.setPostArrayList(new ArrayList<NetForumDataResultDiscussionsDataPost>());
        }
        initRecycler();

    }


    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setNestedScrollingEnabled(false);


        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        binding.recyclerView.setAdapter(new ForumDetailAdapter(ForumDetailActivity.this,
                model.getForumModelArrayList(), model.getPostArrayList(), new OnRecyclerItemClick() {
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


    @Override
    public void closeActivity() {
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
