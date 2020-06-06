package com.copy.lms.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class MessageListModel extends BaseObservable {
    private String noDataLable, noDataMessage, noDataButtonText;
    private ArrayList<MessageModel> arrayList;
    private ArrayList<MessagChatModel> chatModelArrayList;
    private ArrayList<UserModel> userModelArrayList;
    private boolean apiCallActive = false;
    private int page, count;
    private boolean isSwipeRefress;
    private boolean isAddTo;
    private String searchTxt;
    private Drawable noDataImage;
    private String user_id;
    private String thread_id;
    private String last_read;

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

    public ArrayList<MessageModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<MessageModel> arrayList) {
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


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public ArrayList<MessagChatModel> getChatModelArrayList() {
        return chatModelArrayList;
    }

    public void setChatModelArrayList(ArrayList<MessagChatModel> chatModelArrayList) {
        this.chatModelArrayList = chatModelArrayList;
    }

    public String getLast_read() {
        return last_read;
    }

    public void setLast_read(String last_read) {
        this.last_read = last_read;
    }

    public ArrayList<UserModel> getUserModelArrayList() {
        return userModelArrayList;
    }

    public void setUserModelArrayList(ArrayList<UserModel> userModelArrayList) {
        this.userModelArrayList = userModelArrayList;
    }
}
