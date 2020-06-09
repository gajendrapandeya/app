package com.copy.lms.activity;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.copy.lms.BaseAppClass;
import com.copy.lms.Config;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetCurrancyData;
import com.copy.lms.ResponceModel.NetSuccess;
import com.copy.lms.ResponceModel.NetUserProfile;
import com.copy.lms.adapter.DrawerAdapter;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.callBack.OpenFragment;
import com.copy.lms.callBack.TwoButtonListener;
import com.copy.lms.databinding.ActivityMainBinding;
import com.copy.lms.fragment.FragmentHome;
import com.copy.lms.model.Drawer;
import com.copy.lms.model.MainActivityModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AlertDialogAndIntents;
import com.copy.lms.util.AppConstant;
import com.copy.lms.util.Constants;
import com.copy.lms.util.CustomProgressDialog;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends BaseActivity implements MainActivityModel.BottomBtnClick,
        OpenFragment, View.OnClickListener {
    private MainActivityModel model;
    private ActivityMainBinding binding;
    public Fragment currentFragment;
    public int mCurrentSelectedPosition;
    private FragmentHome homeFragment;
    private ArrayList<Drawer> drawerArrayList;
    public ImageView imgDelete, imgAdd;
    private RelativeLayout.LayoutParams menuParam;

    String language;
    CustomProgressDialog dialog;

    public static boolean isOpened = false;
    ActionBarDrawerToggle mDrawerToggle;
    GoogleApiClient googleApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isOpened = true;


        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_books));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_purchases));

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getIcon()) {
                    case R.drawable.ic_books:
                        startActivity(new Intent(MainActivity.this, CourseListActivity.class)
                                .putExtra(CourseListActivity.VALUE, "none"));
                        break;

                    case R.drawable.ic_purchases:
                        startActivity(new Intent(MainActivity.this, MyPurchaseListActivity.class));
                        break;
                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES
                return null;
            }
        });

        bottomNavigation.show(2, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.LANGUAGE_REQUEST_CODE) {
            recreate();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void setModelAndBinding() {

        model = new MainActivityModel(this, this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.included.imgSearch.setOnClickListener(this);
        binding.included.imgBack.setOnClickListener(this);
        binding.included.imgLock.setOnClickListener(this);
        binding.included.searchView.setOnClickListener(this);
        binding.included.imgClose.setOnClickListener(this);
        menuParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .requestIdToken(Config.GOOGLE_SERVER_CLIENT_ID)
                .requestServerAuthCode(Config.GOOGLE_SERVER_CLIENT_ID)
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, null)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        setUpDrawer();
        openHome();
        getUserData();
        getCurrancyData();


    }

    private void setUpDrawer() {
        binding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {

            }

            @Override
            public void onDrawerOpened(View view) {
                updateUserImage();
            }

            @Override
            public void onDrawerClosed(View view) {
                // your refresh code can be called from here

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });


    }


    @Override
    public void setToolBar() {
        setSupportActionBar(binding.included.toolbar);
        binding.included.toolbar.setBackground(new ColorDrawable(ContextCompat.getColor(this, R.color.toolbar_color)));
        binding.included.imgLock.setVisibility(View.VISIBLE);

    }

    @Override
    public void initViews() {
        dialog = new CustomProgressDialog(Constants.PROGRESS_IMAGE, MainActivity.this).createProgressBar();
        mDrawerToggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        setRecyclerView();


    }


    public void closeNavigationDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.START);

    }

    private void updateUserImage() {
        if (drawerArrayList.size() > 0) {
            drawerArrayList.set(0, new Drawer(BaseAppClass.getPreferences().getUserImage(), BaseAppClass.getPreferences().getUserName(), Constants.ACCOUNT, R.drawable.draw_contact, 0, DrawerAdapter.TYPE_HEADER, false, false));
            binding.recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    //set recyclerview of drawer
    private void setRecyclerView() {
        drawerArrayList = new ArrayList<>();

        drawerArrayList.add(0, new Drawer(BaseAppClass.getPreferences().getUserImage(), BaseAppClass.getPreferences().getUserName(), Constants.ACCOUNT, R.drawable.draw_contact, 0, DrawerAdapter.TYPE_HEADER, false, false));
        drawerArrayList.add(new Drawer("", getString(R.string.home), Constants.HOME, R.drawable.draw_home, R.drawable.draw_home, DrawerAdapter.TYPE_ITEM, false, false));
        drawerArrayList.add(new Drawer("", getString(R.string.course), Constants.COURSE, R.drawable.draw_course, R.drawable.draw_course, DrawerAdapter.TYPE_ITEM, true, false));
        drawerArrayList.add(new Drawer("", getString(R.string.bundle), Constants.BUNDLE, R.drawable.draw_course, R.drawable.draw_course, DrawerAdapter.TYPE_ITEM, true, false));
        drawerArrayList.add(new Drawer("", getString(R.string.myPurchase), Constants.MYPURCHASE, R.drawable.draw_purchase, R.drawable.draw_purchase, DrawerAdapter.TYPE_ITEM, true, false));
        drawerArrayList.add(new Drawer("", getString(R.string.certificate), Constants.CERTIFICATE, R.drawable.certificate, R.drawable.certificate, DrawerAdapter.TYPE_ITEM, true, false));

        drawerArrayList.add(new Drawer("", getString(R.string.chat), Constants.MYMESSAGE, R.drawable.testimonial, R.drawable.testimonial, DrawerAdapter.TYPE_ITEM, true, false));
        drawerArrayList.add(new Drawer("", getString(R.string.blog), Constants.BLOG, R.drawable.draw_blog, R.drawable.draw_blog, DrawerAdapter.TYPE_ITEM, true, false));
        drawerArrayList.add(new Drawer("", getString(R.string.forums), Constants.FORUMS, R.drawable.draw_comment, R.drawable.draw_comment, DrawerAdapter.TYPE_ITEM, true, false));
        drawerArrayList.add(new Drawer("", getString(R.string.contactUs), Constants.CONTACT, R.drawable.draw_phone, R.drawable.draw_phone, DrawerAdapter.TYPE_ITEM, true, false));
//        drawerArrayList.add(new Drawer("", getString(R.string.about), Constants.ABOUTUS, R.drawable.draw_info, R.drawable.draw_info, DrawerAdapter.TYPE_ITEM, true, false));
//        drawerArrayList.add(new Drawer("", getString(R.string.feedback), Constants.FEEDBACK, R.drawable.draw_feedback, R.drawable.draw_feedback, DrawerAdapter.TYPE_ITEM, true, false));
        drawerArrayList.add(new Drawer("", getString(R.string.logout), Constants.LOGOUT, R.drawable.lock, R.drawable.lock, DrawerAdapter.TYPE_ITEM, false, false));
//        drawerArrayList.add(new Drawer("", getString(R.string.English), Constants.LANGUAGE, R.drawable.contact, R.drawable.contact, DrawerAdapter.TYPE_EXPAND, true, false));


        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        binding.recyclerView.setAdapter(new DrawerAdapter(MainActivity.this, drawerArrayList, new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {

                DrawerItemClick(position, type);


            }
        }));
        binding.recyclerView.setNestedScrollingEnabled(false);
    }

    private void DrawerItemClick(int position, int type) {

        closeNavigationDrawer();

        switch (drawerArrayList.get(position).getType()) {


            case Constants.ACCOUNT:
                openAccount();
                setSelection(position);
                break;
            case Constants.HOME:
                openHome();
                setSelection(position);
                break;

            case Constants.COURSE:
                openCourseList();
                setSelection(position);
                break;
            case Constants.BUNDLE:
                openBundleList();
                setSelection(position);
                break;

            case Constants.BLOG:
                openBlogList();
                setSelection(position);
                break;

            case Constants.ABOUTUS:
                openAboutus();
                setSelection(position);
                break;

            case Constants.FAQ:
                openFaqList();
                setSelection(position);
                break;

            case Constants.CERTIFICATE:
                openCertificateList();
                setSelection(position);
                break;

            case Constants.MYPURCHASE:
                openMyPurchase();
                setSelection(position);
                break;

            case Constants.FORUMS:
                openForumList();
                setSelection(position);
                break;
            case Constants.MYMESSAGE:
                openChatList();
                setSelection(position);
                break;

            case Constants.CONTACT:
                openContactUs();
                setSelection(position);
                break;

            case Constants.FEEDBACK:
                openContactUs();
                setSelection(position);
                break;

            case Constants.LANGUAGE:
                if (type == Constants.CLICK_ENGLISH)
                    BaseAppClass.getPreferences().saveUserLanguageCode(Constants.ENGLISH);
                else if (type == Constants.CLICK_SPANISH)
                    BaseAppClass.getPreferences().saveUserLanguageCode(Constants.SPANISH);
                else if (type == Constants.CLICK_FRANCH)
                    BaseAppClass.getPreferences().saveUserLanguageCode(Constants.FRANCH);
                else if (type == Constants.CLICK_AREBIC)
                    BaseAppClass.getPreferences().saveUserLanguageCode(Constants.AREBIC);

                BaseAppClass.changeLang(MainActivity.this, BaseAppClass.getPreferences().getUserLanguageCode());
                recreate();
                setSelection(position);
                break;

            case Constants.LOGOUT:
                logOutApi();
                setSelection(position);
                break;


        }

    }

    private void setSelection(int position) {
        for (int i = 0; i < drawerArrayList.size(); i++) {
            drawerArrayList.get(i).setSelected(false);
        }
        drawerArrayList.get(position).setSelected(true);
        binding.recyclerView.getAdapter().notifyDataSetChanged();

    }

    public void openHome() {
        homeFragment = new FragmentHome();
        currentFragment = homeFragment;
        makeFilterFragmentVisible(homeFragment, FragmentHome.TAG);
        mCurrentSelectedPosition = Constants.HOME;

    }

    private void openMyPurchase() {
        startActivity(new Intent(this, MyPurchaseListActivity.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openContactUs() {
        startActivity(new Intent(this, ContactUsActivity.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openAccount() {
        startActivity(new Intent(this, AccountActivity.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openCourseList() {
        startActivity(new Intent(this, CourseListActivity.class)
                .putExtra(CourseListActivity.VALUE, "none"));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openChatList() {
        startActivity(new Intent(MainActivity.this, MassageListActivity.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openBundleList() {
        startActivity(new Intent(this, BundleListActivity.class)
                .putExtra(CourseListActivity.VALUE, "none"));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openBlogList() {
        startActivity(new Intent(this, BlogListActivity.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openAboutus() {

        startActivity(new Intent(this, AboutUsActivity.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);

    }


    private void openCertificateList() {
        startActivity(new Intent(this, CertificateListActivity.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openFaqList() {
        startActivity(new Intent(this, FaqListActivity.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openForumList() {
        startActivity(new Intent(this, ForumListActivity.class));
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            manageBackPress(true);
        }
    }


    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    @Override
    public void onBottomBtnClick(Fragment fragToReplace, String tag) {
        if (model.getStrBottomImgTag().equals(tag))
            return;


    }


    private void manageBackPress(boolean isBackPress) {
        FragmentManager fragMgr = getSupportFragmentManager();

        if (sendBackPressToFragmentOnTop()) {
            return;
        }
        if (fragMgr.getBackStackEntryCount() > 1) {
            fragMgr.popBackStackImmediate();
            Fragment backStackedFrag = getSupportFragmentManager().findFragmentById(R.id.contentFrame);
            backStackedFrag.onResume();
        } else if (binding.included.toolbarSearch.getVisibility() == View.VISIBLE) {
            binding.included.imgClose.performClick();
        } else {
//            AlertDialogAndIntents.closeAppPopup(MainActivity.this);
            if (isBackPress)
                AlertDialogAndIntents.closeDialog(MainActivity.this, new TwoButtonListener() {
                    @Override
                    public void positiveClick() {
                        finish();

                    }

                    @Override
                    public void negativeClick() {

                    }
                });
            else
                openDrawer();
        }
    }

    public void openDrawer() {
        AppConstant.hideKeyboard(MainActivity.this, binding.included.toolbar);
        binding.drawerLayout.openDrawer(binding.leftDrawer);
        if (binding.recyclerView != null && drawerArrayList != null && drawerArrayList.size() > 0)
            binding.recyclerView.scrollToPosition(0);
    }


    public void makeFilterFragmentVisible(Fragment fragmentToShow, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.contentFrame, fragmentToShow);
        ft.addToBackStack(title);
        ft.commit();
    }

    @Override
    public void addNewFragment(Fragment fragToShow, Fragment fragToHide, String TAG) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.contentFrame, fragToShow);
        ft.hide(fragToHide);
        ft.addToBackStack(TAG);
        ft.commit();
    }


    private boolean sendBackPressToFragmentOnTop() {

        return false;
    }

    @Override
    public void setTitleAndToolbar(String title, boolean isBack, boolean isAdd, boolean isCart, boolean isSearch, boolean isNotification, String otherText) {
        setTitle(title);
        binding.included.imgBack.setImageResource(isBack ? R.drawable.btn_bg : R.drawable.menu);
        binding.included.imgSearch.setVisibility(isSearch ? View.VISIBLE : View.GONE);
        binding.included.frameCart.setVisibility(View.GONE);

        binding.included.linMain.post(new Runnable() {
            @Override
            public void run() {
                int width = binding.included.linMain.getWidth();
                if (width > AppConstant.dp2px(MainActivity.this, 56)) {
                    menuParam.setMargins(width, 0, width, 0);
                } else {
                    menuParam.setMargins(AppConstant.dp2px(MainActivity.this, 56), 0, AppConstant.dp2px(MainActivity.this, 56), 0);
                }
                binding.included.txtTitle.setLayoutParams(menuParam);
            }
        });


    }


    private void setTitle(String title) {
        binding.included.txtTitle.setText(title != null ? title : "");
    }

    public Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.contentFrame);
    }


    @Override
    public void setToolbarHeight(int height) {

    }

    @Override
    public void setToolbarMenuIcon(int icon) {


    }

    @Override
    public void selectedData() {

    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.imgBack:
//                onBackPressed();
                manageBackPress(false);
                break;
            case R.id.imgLock:
                Intent intent = new Intent(this, LanguageListActivity.class);
                startActivityForResult(intent, Constants.LANGUAGE_REQUEST_CODE);
                overridePendingTransition(R.anim.animation, R.anim.animation2);
                break;


        }

    }

    public void closeSearchView() {
        binding.included.imgClose.performClick();
    }

    public void openSearch(String str) {
        binding.included.imgSearch.performClick();
        binding.included.searchView.setQuery(str, false);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    public void logOutApi() {
        dialog.setCancelable(false);
        dialog.show();

        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    logout("",
                            callback);
        } else {
            dialog.hide();
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            dialog.hide();
            NetSuccess netSuccess = (NetSuccess) object;
            BaseAppClass.getPreferences().clearUserData();
            startActivity(new Intent(MainActivity.this, SignInActivity.class));

            CookieSyncManager.createInstance(MainActivity.this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeSessionCookie();
            TwitterCore.getInstance().getSessionManager().clearActiveSession();
            finish();


            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {

                        }
                    });
            LoginManager.getInstance().logOut();
            Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();

            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

        }

        @Override
        public void failure(RetrofitError error) {
            dialog.hide();
        }
    };

    public void getUserData() {
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    getProfileData("",
                            userCallback);
        } else {
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();
        }

    }

    private final retrofit.Callback userCallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetUserProfile netUserProfile = (NetUserProfile) object;
            BaseAppClass.getPreferences().saveUserId(netUserProfile.getResult().getId() + "");
            BaseAppClass.getPreferences().saveUserFirstName(netUserProfile.getResult().getFirst_name() + "");
            BaseAppClass.getPreferences().saveUserLastName(netUserProfile.getResult().getLast_name() + "");
            BaseAppClass.getPreferences().saveUserName(netUserProfile.getResult().getFull_name() + "");
            BaseAppClass.getPreferences().savePincode(netUserProfile.getResult().getPincode() + "");
            BaseAppClass.getPreferences().saveUserEmail(netUserProfile.getResult().getEmail() + "");
            BaseAppClass.getPreferences().saveUserMobile(netUserProfile.getResult().getPhone() + "");
            BaseAppClass.getPreferences().saveUserCity(netUserProfile.getResult().getCity() + "");
            BaseAppClass.getPreferences().saveUserState(netUserProfile.getResult().getState() + "");
            BaseAppClass.getPreferences().saveUserCountry(netUserProfile.getResult().getCountry() + "");
            BaseAppClass.getPreferences().saveUserLine1(netUserProfile.getResult().getAddress() + "");
            BaseAppClass.getPreferences().saveUserImage(netUserProfile.getResult().getImage() + "");


        }

        @Override
        public void failure(RetrofitError error) {
            dialog.hide();
        }
    };

    public void getCurrancyData() {
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    getCurrancydata("",
                            currancyCallback);
        } else {
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback currancyCallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetCurrancyData netUserProfile = (NetCurrancyData) object;
            BaseAppClass.getPreferences().saveCurrancyCode(netUserProfile.getResult().getShort_code() + "");
            BaseAppClass.getPreferences().saveCurrancy(netUserProfile.getResult().getSymbol() + "");


        }

        @Override
        public void failure(RetrofitError error) {
            dialog.hide();
        }
    };
}
