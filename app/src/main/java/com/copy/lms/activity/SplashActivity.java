package com.copy.lms.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;
import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.util.AppConstant;


public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    //Widget's
    private LottieAnimationView lottieImage;
    private TextView appName;

    //Var's
    Animation topAnim, bottomAnim;
    SharedPreferences onBoardingScreen;

    private static final int delay = 2500;
    String language = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_splash);

        //Animation Setup
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //widget's initialization
        lottieImage = findViewById(R.id.lottieImage);
        appName = findViewById(R.id.app_name);

        //setting the animation
        lottieImage.setAnimation(topAnim);
        appName.setAnimation(bottomAnim);

        initView();




    }


    private void initView() {
        callHandler(delay);
        BaseAppClass.changeLang(SplashActivity.this, BaseAppClass.getPreferences().getUserLanguageCode());

    }

    @Override
    protected void onResume() {
        BaseAppClass.changeLang(this, BaseAppClass.getPreferences().getUserLanguageCode());
        super.onResume();
    }

    /**
     * Check and starts Location service if not already running.
     */


    public void callHandler(long delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                    checkIsUserLoggedIn();
            }
        }, delay);
    }

    /**
     * Check if the use is logged IN Or Not
     */

    private void checkIsUserLoggedIn() {

        if (!BaseAppClass.getPreferences().isUserLoggedIn()) {

            onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
            boolean isFirstTime = onBoardingScreen.getBoolean("firstTime", true);
            if(isFirstTime) {

                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = onBoardingScreen.edit();
                editor.putBoolean("firstTime", false);
                editor.apply();
                openOnBoardingActivity();
            } else {
                signInActivity();
            }
//                openMainActivity();
        } else {
            if (AppConstant.isOnline(SplashActivity.this))
                openMainActivity();
            else
                Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }


    private void openOnBoardingActivity() {
        startActivity(new Intent(this, onBoardingActivity.class));
        finish();
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void signInActivity() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }


    /**
     * Open's USer Specific Activity
     */

    private void openMainActivity() {
//        AppConstant.openUserSpecificScreen(this);
        startActivity(new Intent(this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.txtRefress:
//                initView();
//                break;
        }
    }
}
