package com.copy.lms.model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.copy.lms.ResponceModel.NetForumDataResultDiscussionsDataPost;

import java.util.ArrayList;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class ForumListModel extends BaseObservable implements Parcelable {
    private String noDataLable, noDataMessage, noDataButtonText;
    private ArrayList<ForumModel> arrayList;
    private ArrayList<NetForumDataResultDiscussionsDataPost> postArrayList;
    private boolean apiCallActive = false;
    private int page, count;
    private boolean isSwipeRefress;
    private boolean isAddTo;
    private String searchTxt;
    private Drawable noDataImage;

    public ForumListModel(){

    }

    protected ForumListModel(Parcel in) {
        noDataLable = in.readString();
        noDataMessage = in.readString();
        noDataButtonText = in.readString();
        arrayList = in.createTypedArrayList(ForumModel.CREATOR);
        apiCallActive = in.readByte() != 0;
        page = in.readInt();
        count = in.readInt();
        isSwipeRefress = in.readByte() != 0;
        isAddTo = in.readByte() != 0;
        searchTxt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noDataLable);
        dest.writeString(noDataMessage);
        dest.writeString(noDataButtonText);
        dest.writeTypedList(arrayList);
        dest.writeByte((byte) (apiCallActive ? 1 : 0));
        dest.writeInt(page);
        dest.writeInt(count);
        dest.writeByte((byte) (isSwipeRefress ? 1 : 0));
        dest.writeByte((byte) (isAddTo ? 1 : 0));
        dest.writeString(searchTxt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ForumListModel> CREATOR = new Creator<ForumListModel>() {
        @Override
        public ForumListModel createFromParcel(Parcel in) {
            return new ForumListModel(in);
        }

        @Override
        public ForumListModel[] newArray(int size) {
            return new ForumListModel[size];
        }
    };

    public ArrayList<NetForumDataResultDiscussionsDataPost> getPostArrayList() {
        return postArrayList;
    }

    public void setPostArrayList(ArrayList<NetForumDataResultDiscussionsDataPost> postArrayList) {
        this.postArrayList = postArrayList;
    }

    public Drawable getNoDataImage() {
        return noDataImage;
    }

    public void setNoDataImage(Drawable noDataImage) {
        this.noDataImage = noDataImage;
        notifyChange();
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

    public ArrayList<ForumModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ForumModel> arrayList) {
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


}
