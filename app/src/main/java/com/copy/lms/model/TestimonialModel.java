package com.copy.lms.model;

import androidx.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;


public class TestimonialModel extends BaseObservable implements Parcelable {



    public TestimonialModel() {
    }
    private String occupation;
    private String updated_at;
    private String name;
    private String created_at;
    private int id;
    private String content;
    private int status;

    protected TestimonialModel(Parcel in) {
        occupation = in.readString();
        updated_at = in.readString();
        name = in.readString();
        created_at = in.readString();
        id = in.readInt();
        content = in.readString();
        status = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(occupation);
        dest.writeString(updated_at);
        dest.writeString(name);
        dest.writeString(created_at);
        dest.writeInt(id);
        dest.writeString(content);
        dest.writeInt(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TestimonialModel> CREATOR = new Creator<TestimonialModel>() {
        @Override
        public TestimonialModel createFromParcel(Parcel in) {
            return new TestimonialModel(in);
        }

        @Override
        public TestimonialModel[] newArray(int size) {
            return new TestimonialModel[size];
        }
    };

    public String getOccupation() {
        return this.occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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