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
import com.copy.lms.ResponceModel.NetTestimonialData;
import com.copy.lms.ResponceModel.NetTestimonialDataResultData;
import com.copy.lms.adapter.TestimonialListAdapter;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.ActivityTestimoniallistBinding;
import com.copy.lms.model.TestimonialListModel;
import com.copy.lms.model.TestimonialModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class TestimonialListActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    private TestimonialListModel model;
    private ActivityTestimoniallistBinding binding;


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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_testimoniallist);
        model = new TestimonialListModel();
        model.setArrayList(new ArrayList<TestimonialModel>());
        binding.setTestimonialListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.testimonial));
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
        binding.included.imgBack.setOnClickListener(this);
        initRecycler();
        sponsorListApi();
    }

    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        binding.recyclerView.setAdapter(new TestimonialListAdapter(TestimonialListActivity.this,
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


    public void sponsorListApi() {
        binding.progressBar.setVisibility(View.VISIBLE);
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    getTestimonialList("",
                            callback);
        } else {
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            binding.progressBar.setVisibility(View.GONE);
            NetTestimonialData netTestimonialData = (NetTestimonialData) object;
            if (netTestimonialData.getStatus().equalsIgnoreCase("success")) {
                fillArrayList(netTestimonialData.getResult().getData());

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

    private void fillArrayList(List<NetTestimonialDataResultData> items) {
        model.getArrayList().clear();


        TestimonialModel itemModel;


        for (int i = 0; i < items.size(); i++) {
            itemModel = new TestimonialModel();
            itemModel.setName(items.get(i).getName());
            itemModel.setId(items.get(i).getId());
            itemModel.setCreated_at(items.get(i).getCreated_at());
            itemModel.setStatus(items.get(i).getStatus());
            itemModel.setUpdated_at(items.get(i).getUpdated_at());
            itemModel.setContent(items.get(i).getContent());
            itemModel.setOccupation(items.get(i).getOccupation());
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
