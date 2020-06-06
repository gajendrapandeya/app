package com.copy.lms.model;

import android.graphics.drawable.Drawable;

import com.copy.lms.ResponceModel.NetForumDataResultDiscussionsDataPost;

import java.util.ArrayList;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class ForumDetailListModel extends BaseObservable {
    private String noDataLable, noDataMessage, noDataButtonText;
    private ArrayList<ForumDetailModel> arrayList;
    private ArrayList<ForumModel> forumModelArrayList;
    private ArrayList<NetForumDataResultDiscussionsDataPost> postArrayList;
    private boolean apiCallActive = false;
    private int page, count;
    private boolean isSwipeRefress;
    private boolean isAddTo;
    private String searchTxt;
    private Drawable noDataImage;

    public Drawable getNoDataImage() {
        return noDataImage;
    }

    public void setNoDataImage(Drawable noDataImage) {
        this.noDataImage = noDataImage;
        notifyChange();
    }

    public ArrayList<NetForumDataResultDiscussionsDataPost> getPostArrayList() {
        return postArrayList;
    }

    public void setPostArrayList(ArrayList<NetForumDataResultDiscussionsDataPost> postArrayList) {
        this.postArrayList = postArrayList;
    }

    public String getNoDataLable() {
        return noDataLable;
    }

    public void setNoDataLable(String noDataLable) {
        this.noDataLable = noDataLable;
    }

    public String getNoDataMessage() {
        return noDataMessage;
    }

    public void setNoDataMessage(String noDataMessage) {
        this.noDataMessage = noDataMessage;
    }

    public String getNoDataButtonText() {
        return noDataButtonText;
    }

    public void setNoDataButtonText(String noDataButtonText) {
        this.noDataButtonText = noDataButtonText;
    }

    public ArrayList<ForumDetailModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ForumDetailModel> arrayList) {
        this.arrayList = arrayList;
    }

    @Bindable
    public boolean isApiCallActive() {
        return apiCallActive;
    }

    public void setApiCallActive(boolean apiCallActive) {
        this.apiCallActive = apiCallActive;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSwipeRefress() {
        return isSwipeRefress;
    }

    public void setSwipeRefress(boolean swipeRefress) {
        isSwipeRefress = swipeRefress;
    }

    public boolean isAddTo() {
        return isAddTo;
    }

    public void setAddTo(boolean addTo) {
        isAddTo = addTo;
    }

    public String getSearchTxt() {
        return searchTxt;
    }

    public void setSearchTxt(String searchTxt) {
        this.searchTxt = searchTxt;
    }

    public ArrayList<ForumModel> getForumModelArrayList() {
        return forumModelArrayList;
    }

    public void setForumModelArrayList(ArrayList<ForumModel> forumModelArrayList) {
        this.forumModelArrayList = forumModelArrayList;
    }
}
