package com.copy.lms.activity;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;


import com.copy.lms.CustomDialog;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.copy.lms.BaseAppClass;
import com.copy.lms.Config;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetForgot;
import com.copy.lms.ResponceModel.TokenModel;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.TwoButtonListener;
import com.copy.lms.databinding.ActivitySigninBinding;
import com.copy.lms.model.SignInModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AccessTokenTask;
import com.copy.lms.util.AlertDialogAndIntents;
import com.copy.lms.util.AppConstant;
import com.copy.lms.util.Constants;
import com.copy.lms.util.CustomProgressDialog;
import com.copy.lms.util.Utility;
import com.copy.lms.util.Validation;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.util.Arrays;
import java.util.Objects;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignInActivity extends BaseActivity implements SignInModel.BtnClick, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private SignInModel loginModel;
    private ActivitySigninBinding binding;
    public static final String EXTRA_USERNAME = "userName";
    public static final String EXTRA_PASSWORD = "password";
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;

    private static SignInActivity instance;

    //AlertDialog
    private CustomDialog customDialog;

    public static SignInActivity getInstance() {
        return instance;
    }


    private static final String EMAIL = "email";
    private static final String USER_POSTS = "user_posts";
    private static final String AUTH_TYPE = "rerequest";

    private CallbackManager mCallbackManager;
    private TwitterAuthClient client;

    boolean isShow;
    CustomProgressDialog dialog;

    @Override
    protected void onResume() {
        BaseAppClass.changeLang(this, BaseAppClass.getPreferences().getUserLanguageCode());
        super.onResume();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        instance = this;


        //AlertDialog
        customDialog = new CustomDialog(this);

    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signin);
        setModel();
    }

    @Override
    public void setToolBar() {

    }

    @Override
    public void initViews() {
        client = new TwitterAuthClient();
        dialog = new CustomProgressDialog(Constants.PROGRESS_TEXT, SignInActivity.this).createProgressBar();

        mCallbackManager = CallbackManager.Factory.create();

        String serverClientId = Config.GOOGLE_SERVER_CLIENT_ID;

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .requestIdToken(serverClientId)
                .requestServerAuthCode(serverClientId)
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        binding.etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    return true;
                }
                return false;
            }
        });

        binding.btnSignin.setOnClickListener(this);
        binding.showPassword.setOnClickListener(this);
        binding.btnSignUp.setOnClickListener(this);
        binding.forgot.setOnClickListener(this);
        binding.loginGoogle.setOnClickListener(this);
        binding.loginTwitter.setOnClickListener(this);
        binding.demoFb.setOnClickListener(this);
// Set the initial permissions to request from the user while logging in
        binding.loginButton.setReadPermissions(Arrays.asList(EMAIL, USER_POSTS));

        binding.loginButton.setAuthType(AUTH_TYPE);

        // Register a callback to respond to the user
        binding.loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                AccessToken accessToken = loginResult.getAccessToken();
                socialLogin("facebook", accessToken.getToken());
            }

            @Override
            public void onCancel() {
                setResult(RESULT_CANCELED);
            }

            @Override
            public void onError(FacebookException e) {
                // Handle exception
            }
        });

    }

    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignin:
                if (validateInput())
                    loginApi();
                break;

            case R.id.btnSignUp:
                gotSignUp();
                break;
            case R.id.loginTwitter:

                if (AppConstant.isOnline(SignInActivity.this)) {
                    customLoginTwitter();
                } else {
                    Toast.makeText(instance, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.showPassword:
                if (isShow) {
                    isShow = false;
                    binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.etPassword.setSelection(binding.etPassword.getText().length());

                } else {
                    isShow = true;
                    binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.etPassword.setSelection(binding.etPassword.getText().length());
                }
                break;

            case R.id.loginGoogle:

                if (AppConstant.isOnline(SignInActivity.this)) {
                    Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                    startActivityForResult(intent, RC_SIGN_IN);
                } else {
                    Toast.makeText(instance, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.demoFb:
                if (AppConstant.isOnline(SignInActivity.this)) {

                    binding.loginButton.performClick();
                } else {
                    Toast.makeText(instance, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.forgot:
                AlertDialogAndIntents.forgotDialog(SignInActivity.this, new TwoButtonListener() {
                    @Override
                    public void positiveClick() {
                        forgotApi();
                    }

                    @Override
                    public void negativeClick() {

                    }
                });
                break;

        }
    }


    private boolean validateInput() {
        if (!Validation.isStringEmpty(binding.etEmailorMobile.getText().toString())) {
            if (Utility.validate(binding.etEmailorMobile.getText().toString())) {
                if (!Validation.isStringEmpty(binding.etPassword.getText().toString())) {
                    return true;
                } else
                    Toast.makeText(this, getString(R.string.enterpassword), Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, getString(R.string.validemail), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, getString(R.string.enteremail), Toast.LENGTH_SHORT).show();


        return false;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Log.e("data", data.toString());
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        } else if (requestCode == 140) {
            Log.e("data", data.toString());
            client.getRequestCode();
            if (client != null)
                client.onActivityResult(requestCode, resultCode, data);

            fetchTwitterEmail(getTwitterSession());

        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);

        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            final GoogleSignInAccount account = result.getSignInAccount();
            new AccessTokenTask(account).execute();

        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.signcancel), Toast.LENGTH_LONG).show();
        }
    }

    public void gotoProfile(String token) {
        socialLogin("google", token);
    }

    private void gotSignUp() {
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(getString(R.string.email_hint), loginModel.getEmail());
        savedInstanceState.putString(getString(R.string.password_hint), loginModel.getPass());
    }


    private void setModel() {
        loginModel = new SignInModel(this);

    }


    @Override
    public void onSignUpBtnClick() {
//        startActivityForResult(new Intent(this, SignUpActivity.class), Constants.LOGIN_REQUEST_CODE);
//        //  startActivityForResult(new Intent(this, SignUpAnimActivity.class), CustomConstants.LOGIN_REQUEST_CODE);
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    /**
     * Open"s Main Activity {@link MainActivity}
     */
    private void openMainActivity() {

        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.animation, R.anim.animation2);

    }


    //     Login Api Codeall
    public void loginApi() {
        dialog.setCancelable(false);
      //  dialog.show();
        customDialog.startLoading();
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    passwordLogin("password",
                            Config.CLIENT_ID,
                            Config.CLIENT_SECRET,
                            binding.etEmailorMobile.getText().toString(),
                            binding.etPassword.getText().toString(),
                            "*",
                            callback);
        } else {
            dialog.hide();
            customDialog.dissmissDialog();
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    //     Login Api Codeall
    public void socialLogin(String provider, String token) {
        dialog.setCancelable(false);
        customDialog.startLoading();
        dialog.show();
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    socialLogin("social",
                            Config.CLIENT_ID,
                            Config.CLIENT_SECRET,
                            "*",
                            provider,
                            token,

                            callback);
        } else {
            dialog.hide();
            customDialog.dissmissDialog();
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }  //     Login Api Codeall

    public void twitterLogin(String provider, String token, String secret) {
        dialog.setCancelable(false);
        customDialog.startLoading();
        dialog.show();
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    twitterLogin("social",
                            Config.CLIENT_ID,
                            Config.CLIENT_SECRET,
                            "*",
                            provider,
                            token,
                            secret,

                            callback);
        } else {
            dialog.hide();
            customDialog.dissmissDialog();
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            dialog.hide();
            customDialog.dissmissDialog();
            TokenModel tokenModel = (TokenModel) object;
            if (tokenModel != null) {
                BaseAppClass.getPreferences().saveToken(tokenModel.getAccess_token());
                BaseAppClass.getPreferences().saveIsTokenAvailable(true);
                openMainActivity();

            } else {
                Toast.makeText(SignInActivity.this, getString(R.string.somthingwrong), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            dialog.hide();
            customDialog.dissmissDialog();
            Toast.makeText(SignInActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };


    //     Login Api Codeall
    public void forgotApi() {
        dialog.setCancelable(false);
        dialog.show();
        customDialog.startLoading();
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    forgotPassword(binding.etEmailorMobile.getText().toString(),
                            forgotcallback);
        } else {
            dialog.hide();
            customDialog.dissmissDialog();
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback forgotcallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            dialog.hide();
            customDialog.dissmissDialog();
            NetForgot netForgot = (NetForgot) object;
            if (netForgot != null) {
                Toast.makeText(SignInActivity.this, getString(R.string.checkEmail), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(SignInActivity.this, getString(R.string.somthingwrong), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            dialog.hide();
            customDialog.dissmissDialog();
            Toast.makeText(SignInActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };


    /**
     * method to do Default Twitter Login
     */

    public void customLoginTwitter() {
        //check if user is already authenticated or not
        if (getTwitterSession() == null) {

            //if user is not authenticated start authenticating
            client.authorize(this, new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {

                    // Do something with result, which provides a TwitterSession for making API calls
                    TwitterSession twitterSession = result.data;
                    fetchTwitterEmail(twitterSession);
                }

                @Override
                public void failure(TwitterException e) {
                    // Do something on failure
                    Toast.makeText(SignInActivity.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //if user is already authenticated direct call fetch twitter email api
            Toast.makeText(this, "User already authenticated", Toast.LENGTH_SHORT).show();
//            fetchTwitterEmail(getTwitterSession());
        }
    }

    /**
     * get authenticates user session
     *
     * @return twitter session
     */
    private TwitterSession getTwitterSession() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

        return session;
    }

    /**
     * Before using this feature, ensure that “Request email addresses from users” is checked for your Twitter app.
     *
     * @param twitterSession user logged in twitter session
     */
    public void fetchTwitterEmail(final TwitterSession twitterSession) {
        client.requestEmail(twitterSession, new Callback<String>() {
            @Override
            public void success(Result<String> result) {
                //here it will give u only email and rest of other information u can get from TwitterSession
                Log.e("User Id : ", twitterSession.getUserName() + "");
                Log.e("data", "User Id : " + twitterSession.getUserId() + "\nScreen Name : " + twitterSession.getUserName() + "\nEmail Id : " + result.data);
                twitterLogin("twitter", twitterSession.getAuthToken().token, twitterSession.getAuthToken().secret);
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(SignInActivity.this, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
