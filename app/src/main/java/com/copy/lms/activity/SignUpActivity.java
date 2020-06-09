package com.copy.lms.activity;

import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.copy.lms.BaseAppClass;
import com.copy.lms.CustomDialog;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetSignUpData;
import com.copy.lms.ResponceModel.NetSuccess;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.databinding.ActivitySignupBinding;
import com.copy.lms.model.AccountDetailModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;
import com.copy.lms.util.Constants;
import com.copy.lms.util.CustomProgressDialog;
import com.copy.lms.util.Utility;
import com.copy.lms.util.Validation;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {
    private AccountDetailModel model;
    private ActivitySignupBinding binding;

    //AlertDialog
    private CustomDialog customDialog;

    CustomProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //AlertDialog
        customDialog = new CustomDialog(this);


    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        model = new AccountDetailModel();

    }
    @Override
    protected void onResume() {
        BaseAppClass.changeLang(this, BaseAppClass.getPreferences().getUserLanguageCode());
        super.onResume();
    }

    @Override
    public void setToolBar() {
//        binding.included.txtTitle.setText(getString(R.string.signUp));
//        binding.included.imgBack.setOnClickListener(this);

    }

    @Override
    public void initViews() {
        dialog = new CustomProgressDialog(Constants.PROGRESS_IMAGE, SignUpActivity.this).createProgressBar();

        binding.btnSignUp.setOnClickListener(this);
        binding.btnSignIn.setOnClickListener(this);

    }

    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    //     Login Api Codeall
    public void getSignudata() {

        RetrofitClient.getInstance().getRestOkClient().
                getSignupForm("",
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetSignUpData netAboutData = (NetSignUpData) object;
            if (netAboutData != null) {

            } else {
                Toast.makeText(SignUpActivity.this, getString(R.string.somthingwrong), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };


    private boolean validateInput() {
        if (!Validation.isStringEmpty(binding.edtFname.getText().toString())) {
            if (!Validation.isStringEmpty(binding.edtLname.getText().toString())) {
                if (Validation.isPasswordValid(binding.edtpass.getText().toString())) {
                if (Utility.validate(binding.edtEmail.getText().toString())) {
                    return true;
                } else
                    Toast.makeText(this, getString(R.string.enteremail), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, getString(R.string.enterpassword), Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, getString(R.string.enterLname), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, getString(R.string.enterFname), Toast.LENGTH_SHORT).show();


        return false;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                closeActivity();
                break;

            case R.id.btnSignUp:
                if (validateInput())
                    signUpAPi();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }


    //     Login Api Codeall
    public void signUpAPi() {
        dialog.setCancelable(false);
        customDialog.startLoading();
        dialog.show();
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    signUp(binding.edtFname.getText().toString(),
                            binding.edtLname.getText().toString(),
                            binding.edtEmail.getText().toString(),
                            binding.edtpass.getText().toString(),
//                        binding.edtPhone.getText().toString(),
//                        "",
//                        "",
//                        binding.edtAdd.getText().toString(),
                            binding.edtCity.getText().toString(),
//                        binding.edtpin.getText().toString(),
//                        binding.edtState.getText().toString(),
//                        binding.edtcountry.getText().toString(),
//                        ""
                            signUpcallback);
        } else {
            dialog.hide();
            customDialog.dissmissDialog();
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback signUpcallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            dialog.hide();
            customDialog.dissmissDialog();
            NetSuccess NetSuccess = (NetSuccess) object;
            if (NetSuccess != null) {

                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                Toast.makeText(SignUpActivity.this, getString(R.string.signUpSuccess), Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(SignUpActivity.this, getString(R.string.somthingwrong), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            dialog.hide();
            customDialog.dissmissDialog();
            Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };


}
