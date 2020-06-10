package com.copy.lms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.adapter.LanguageListAdapter;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.ActivityLanguagelistBinding;
import com.copy.lms.model.LanguageListModel;
import com.copy.lms.model.LanguageModel;
import com.copy.lms.util.AppConstant;
import com.copy.lms.util.Constants;

import java.util.ArrayList;
import java.util.Objects;

public class LanguageListActivity extends BaseActivity implements View.OnClickListener {

    public static final String VALUE = "value";
    private LanguageListModel model;
    private ActivityLanguagelistBinding binding;


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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_languagelist);
        model = new LanguageListModel();
        model.setArrayList(new ArrayList<LanguageModel>());
        binding.setLanguageListModel(model);

    }

    @Override
    public void setToolBar() {

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
        initRecycler();
        fillArrayList();

    }


    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new LanguageListAdapter(LanguageListActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                if (position == 0)
                    BaseAppClass.getPreferences().saveUserLanguageCode(Constants.ENGLISH);
//                else if (position == 1)
//                    BaseAppClass.getPreferences().saveUserLanguageCode(Constants.SPANISH);
//                else if (position == 2)
//                    BaseAppClass.getPreferences().saveUserLanguageCode(Constants.FRANCH);
//                else if (position == 3)
//                    BaseAppClass.getPreferences().saveUserLanguageCode(Constants.AREBIC);
//                else if(position == 4)
//                    BaseAppClass.getPreferences().saveUserLanguageCode(Constants.NEPALI);

                BaseAppClass.changeLang(LanguageListActivity.this, BaseAppClass.getPreferences().getUserLanguageCode());
                setResult();
            }
        }));



    }

    private void setResult() {
        Intent i = new Intent();
        setResult(RESULT_OK, i);
        closeActivity();
    }



    /*set language list*/

    private void fillArrayList() {
        binding.progressBar.setVisibility(View.GONE);


        LanguageModel itemModel;

        String[] languageName = {getString(R.string.English), getString(R.string.nepali)};

        int[] languageId = {Constants.ENGLISH, Constants.NEPALI};


        for (int i = 0; i < 2; i++) {
            itemModel = new LanguageModel();
            itemModel.setCode(languageId[i]);
            itemModel.setName(languageName[i]);
            itemModel.setIsselected(BaseAppClass.getPreferences().getUserLanguageCode() == languageId[i]);
            model.getArrayList().add(itemModel);


        }
        Objects.requireNonNull(binding.recyclerView.getAdapter()).notifyDataSetChanged();
        notyFyDat();

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
