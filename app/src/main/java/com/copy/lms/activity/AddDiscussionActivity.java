package com.copy.lms.activity;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetSuccess;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.databinding.ActivityAddDiscussionBinding;
import com.copy.lms.model.AddForumModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;
import com.copy.lms.util.Constants;
import com.copy.lms.util.CustomProgressDialog;
import com.copy.lms.util.Validation;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddDiscussionActivity extends BaseActivity implements View.OnClickListener {
    private AddForumModel model;
    private ActivityAddDiscussionBinding binding;
    CustomProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    protected void onResume() {
        BaseAppClass.changeLang(this, BaseAppClass.getPreferences().getUserLanguageCode());
        super.onResume();
    }
    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_discussion);
        model = new AddForumModel();

    }

    @Override
    public void setToolBar() {
        binding.included.txtTitle.setText(getString(R.string.forums));
        binding.included.imgBack.setOnClickListener(this);

    }

    @Override
    public void initViews() {
        dialog = new CustomProgressDialog(Constants.PROGRESS_IMAGE, AddDiscussionActivity.this).createProgressBar();
        binding.addDiscussion.setOnClickListener(this);

    }

    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    //     Login Api Codeall
    public void addforumDiscussion() {
        dialog.setCancelable(false);
        dialog.show();
        if (AppConstant.isOnline(this)){
        RetrofitClient.getInstance().getRestOkClient().
                addDiscussion(binding.edttitle.getText().toString(),
                        binding.edtDiscussion.getText().toString(),
                        "1",
                        callback);
        }
        else {
            dialog.hide();
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            dialog.hide();
            NetSuccess netSuccess = (NetSuccess) object;

            if (netSuccess != null) {
                Toast.makeText(AddDiscussionActivity.this, getString(R.string.addSuccessFul), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(AddDiscussionActivity.this, getString(R.string.somthingwrong), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            dialog.hide();
            Toast.makeText(AddDiscussionActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                closeActivity();
                break;

            case R.id.addDiscussion:
                if (validateInput())
                    addforumDiscussion();
                break;

        }
    }

    private boolean validateInput() {
        if (!Validation.isStringEmpty(binding.edttitle.getText().toString())) {

            if (!Validation.isStringEmpty(binding.edtDiscussion.getText().toString())) {
                return true;
            } else
                Toast.makeText(this, getString(R.string.enterDiscussion), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, getString(R.string.enterTitle), Toast.LENGTH_SHORT).show();


        return false;

    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }


}
