package com.copy.lms.model;

import androidx.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;


public class WhyusModel extends BaseObservable implements Parcelable {



    public WhyusModel() {
    }
    private String updated_at;
    private String icon;
    private String created_at;
    private String  id;
    private String title;
    private String content;
    private int status;

    protected WhyusModel(Parcel in) {
        updated_at = in.readString();
        icon = in.readString();
        created_at = in.readString();
        id = in.readString();
        title = in.readString();
        content = in.readString();
        status = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(updated_at);
        dest.writeString(icon);
        dest.writeString(created_at);
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeInt(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WhyusModel> CREATOR = new Creator<WhyusModel>() {
        @Override
        public WhyusModel createFromParcel(Parcel in) {
            return new WhyusModel(in);
        }

        @Override
        public WhyusModel[] newArray(int size) {
            return new WhyusModel[size];
        }
    };

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

  }