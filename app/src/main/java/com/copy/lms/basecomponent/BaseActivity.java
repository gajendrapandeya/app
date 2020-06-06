package com.copy.lms.basecomponent;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {

    private static final int REQUEST_CHECK_SETTINGS_GPS = 1;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
//        CustomMessageView.getInstance().init(this);
        setModelAndBinding();
        initViews();
        setToolBar();
    }

    //Used to set toolbar of the current Activity
    public abstract void setModelAndBinding();

    //Used to set toolbar of the current Activity
    public abstract void setToolBar();

    //Used to initialize views
    public abstract void initViews();


    //Used to Close Activity
    public abstract void closeActivity();

    @Override
    public void onBackPressed() {
        closeActivity();
    }




}
