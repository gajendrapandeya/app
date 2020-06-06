package com.copy.lms.model;

import androidx.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;


public class CourseDetailModel extends BaseObservable implements Parcelable {
    private String description;
    private String id;
    private boolean completed;
    private String title;
    private String type;

    public CourseDetailModel(){

    }

    protected CourseDetailModel(Parcel in) {
        description = in.readString();
        id = in.readString();
        completed = in.readByte() != 0;
        title = in.readString();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(id);
        dest.writeByte((byte) (completed ? 1 : 0));
        dest.writeString(title);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CourseDetailModel> CREATOR = new Creator<CourseDetailModel>() {
        @Override
        public CourseDetailModel createFromParcel(Parcel in) {
            return new CourseDetailModel(in);
        }

        @Override
        public CourseDetailModel[] newArray(int size) {
            return new CourseDetailModel[size];
        }
    };

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }}