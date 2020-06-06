package com.copy.lms.activity;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.OpenFragment;
import com.copy.lms.databinding.ActivityInvoiceBinding;
import com.copy.lms.fragment.InvoiceFragment;
import com.copy.lms.model.InvoiceMainModel;

import java.util.ArrayList;
import java.util.List;


public class InvoiceActivity extends BaseActivity implements View.OnClickListener , OpenFragment {

    private InvoiceMainModel model;
    private ActivityInvoiceBinding binding;


    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invoice);
        binding.setInvoiceModel(model);
    }
    @Override
    protected void onResume() {
        BaseAppClass.changeLang(this, BaseAppClass.getPreferences().getUserLanguageCode());
        super.onResume();
    }
    @Override
    public void setToolBar() {
        binding.included.txtTitle.setText(getString(R.string.invoice));
        binding.included.imgBack.setOnClickListener(this);
        binding.included.imgBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void initViews() {
        setupViewPager(binding.viewpager);
        binding.tabs.setupWithViewPager(binding.viewpager);


    }

    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgBack:
                closeActivity();
                break;


        }

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new InvoiceFragment(), getString(R.string.all));
        adapter.addFragment(new InvoiceFragment(), getString(R.string.ourstanding));
        adapter.addFragment(new InvoiceFragment(), getString(R.string.paid));
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void addNewFragment(Fragment fragToShow, Fragment fragToHide, String TAG) {

    }

    @Override
    public void setTitleAndToolbar(String title, boolean isBack, boolean isSearch, boolean isSetting, boolean isLocation, boolean isCart, String otherText) {

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
}