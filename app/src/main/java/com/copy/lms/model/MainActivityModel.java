package com.copy.lms.model;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.fragment.app.Fragment;


/**
 * Created by rahul.patel on 11/29/2016.
 */
public class MainActivityModel extends BaseObservable {
    private String strBottomImgTag;
    private BottomBtnClick bottomBtnClick;
    private Context context;
    private boolean isBtmVisible = true;
    private int chatCount;

    public MainActivityModel(){}

    public MainActivityModel(Context context, BottomBtnClick bottomBtnClick) {
        this.context = context;
        this.bottomBtnClick = bottomBtnClick;
    }

    public interface BottomBtnClick {
        void onBottomBtnClick(Fragment fragToReplace, String tag);
    }

    @Bindable
    public String getStrBottomImgTag() {
        return strBottomImgTag;
    }

    public void setStrBottomImgTag(String strBottomImgTag) {
        this.strBottomImgTag = strBottomImgTag;
        notifyChange();
    }

    @Bindable
    public int getChatCount() {
        return chatCount;
    }

    public void setChatCount(int chatCount) {
        this.chatCount = chatCount;
        notifyChange();
    }

    @Bindable
    public boolean isBtmVisible() {
        return isBtmVisible;
    }

    public void setBtmVisible(boolean btmVisible) {
        isBtmVisible = btmVisible;
//        notifyPropertyChanged(BR.isBtmVisible);
        notifyChange();
    }

    /**
     * Sets the current Tag in {@link strBottomImgTag} if the Bottom Btn is Clicked
     */
    public void changeBottomBtnImage(String tag) {
        showFragment(tag);
    }

    /**
     * set the current Tag in{@link strBottomImgTag} when bottom btn is click but mot change the previous button click
     */
    public void notChangeBottomBtnImage(String tag) {
        showFragment(tag);
    }

    /**
     * Fragments are
     * replaced using the tags in Main Activity
     */

    private void showFragment(String tag) {
        if (bottomBtnClick != null) {
            bottomBtnClick.onBottomBtnClick(null, tag);
        }
    }


}
