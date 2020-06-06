package com.copy.lms.callBack;

import androidx.fragment.app.Fragment;


public interface OpenFragment {
    void addNewFragment(Fragment fragToShow, Fragment fragToHide, String TAG);

    void setTitleAndToolbar(String title, boolean isBack, boolean isSearch, boolean isSetting, boolean isLocation, boolean isCart, String otherText);

    void setToolbarHeight(int height);

    void setToolbarMenuIcon(int icon);

    void selectedData();
    /* void setToolbarMenuOption(int count,boolean isPredefineMsg);*/
}
