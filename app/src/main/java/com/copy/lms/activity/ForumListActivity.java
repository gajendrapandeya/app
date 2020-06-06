package com.copy.lms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetForumData;
import com.copy.lms.ResponceModel.NetForumDataResultDiscussionsData;
import com.copy.lms.ResponceModel.NetSuccess;
import com.copy.lms.adapter.ForumListAdapter;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.ActivityForumlistBinding;
import com.copy.lms.model.ForumListModel;
import com.copy.lms.model.ForumModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;
import com.copy.lms.util.Constants;
import com.copy.lms.util.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ForumListActivity extends BaseActivity implements View.OnClickListener {

    private ForumListModel model;
    private ActivityForumlistBinding binding;

    CustomProgressDialog dialog;

    int removePos;

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forumlist);
        model = new ForumListModel();
        model.setArrayList(new ArrayList<ForumModel>());
        binding.setForumListModel(model);

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
        dialog = new CustomProgressDialog(Constants.PROGRESS_IMAGE, ForumListActivity.this).createProgressBar();
        binding.included.imgBack.setOnClickListener(this);
        binding.addforum.setOnClickListener(this);
        initRecycler();
        forumListApi();

    }


    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new ForumListAdapter(ForumListActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                switch (type) {
                    case Constants.ROW_CLICK:
                        Intent intent = new Intent(ForumListActivity.this, ForumDetailActivity.class);
                        intent.putExtra(ForumDetailActivity.POS, position);
                        intent.putExtra(ForumDetailActivity.ID, model.getArrayList().get(position).getId() + "  ");
                        intent.putExtra(ForumDetailActivity.HEADER, model.getArrayList().get(position).getTitle() + "  ");
                        intent.putExtra(ForumDetailActivity.DES, Html.fromHtml(model.getArrayList().get(position).getPost().get(0).getBody()) + "  ");
                        intent.putExtra(ForumDetailActivity.COUNT, model.getArrayList().get(position).getPost().size());
                        intent.putParcelableArrayListExtra(ForumDetailActivity.FORUMDATA, model.getArrayList());
                        intent.putParcelableArrayListExtra(ForumDetailActivity.POSTDATA, model.getArrayList().get(position).getPost());
                        startActivity(intent);
                        overridePendingTransition(R.anim.animation, R.anim.animation2);
                        break;
                    case Constants.DELETE:
                        removePos = position;
                        deleteForum(model.getArrayList().get(position).getId() + "");
                        break;
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


    public void forumListApi() {
        binding.progressBar.setVisibility(View.VISIBLE);
        model.getArrayList().clear();
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    getForumList("",
                            callback);
        } else {
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            binding.progressBar.setVisibility(View.GONE);

            NetForumData forumData = (NetForumData) object;
            if (forumData.getStatus().equalsIgnoreCase("success")) {
                fillArrayList(forumData.getResult().getDiscussions().getData());

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

    private void fillArrayList(List<NetForumDataResultDiscussionsData> items) {
        binding.progressBar.setVisibility(View.GONE);


        ForumModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new ForumModel();
            itemModel.setId(items.get(i).getId());
            itemModel.setAnswered(items.get(i).getAnswered());
            itemModel.setTitle(items.get(i).getTitle());
            itemModel.setCategory(items.get(i).getCategory());
            itemModel.setChatter_category_id(items.get(i).getChatter_category_id());
            itemModel.setLast_reply_at(items.get(i).getLast_reply_at());
            itemModel.setColor(items.get(i).getColor());
            itemModel.setUpdated_at(items.get(i).getUpdated_at());
            itemModel.setDeleted_at(items.get(i).getDeleted_at());
            itemModel.setCreated_at(items.get(i).getCreated_at());

            for (int j = 0; j < items.get(i).getPost().size(); j++) {
                itemModel.setPost(items.get(i).getPost());
                itemModel.setBody(items.get(i).getPost().get(j).getBody());
//                itemModel.setFirst_name(items.get(i).getPost().get(j).getUser().getFirst_name());
//                itemModel.setImage(items.get(i).getPost().get(j).getUser().getImage());

            }

            itemModel.setPosts_count(items.get(i).getPosts_count());
            itemModel.setTitle(items.get(i).getTitle());
            itemModel.setUser_id(items.get(i).getUser_id());
            itemModel.setUser(items.get(i).getUser());
            itemModel.setSlug(items.get(i).getSlug());
            itemModel.setViews(items.get(i).getViews());

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

    //    Delete forum
    public void deleteForum(String id) {
        dialog.setCancelable(false);
        dialog.show();
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    deleteForum(id,
                            deletecallback);
        } else {
            dialog.hide();
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback deletecallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            dialog.hide();
            NetSuccess netSuccess = (NetSuccess) object;

            if (netSuccess != null) {
                Toast.makeText(ForumListActivity.this, getString(R.string.deletSuccessFul), Toast.LENGTH_SHORT).show();
                model.getArrayList().remove(removePos);
                binding.recyclerView.getAdapter().notifyDataSetChanged();


            } else {
                Toast.makeText(ForumListActivity.this, getString(R.string.somthingwrong), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            dialog.hide();
            Toast.makeText(ForumListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };

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
            case R.id.addforum:
                Intent intent = new Intent(ForumListActivity.this, AddDiscussionActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.animation, R.anim.animation2);
                break;

        }
    }
}
