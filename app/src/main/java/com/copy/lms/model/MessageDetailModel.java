package com.copy.lms.model;

import androidx.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;


public class MessageDetailModel extends BaseObservable implements Parcelable {

    private String fName, lName;
    private int image;

    public MessageDetailModel() {
    }

    protected MessageDetailModel(Parcel in) {
        fName = in.readString();
        lName = in.readString();
        image = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fName);
        dest.writeString(lName);
        dest.writeInt(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MessageDetailModel> CREATOR = new Creator<MessageDetailModel>() {
        @Override
        public MessageDetailModel createFromParcel(Parcel in) {
            return new MessageDetailModel(in);
        }

        @Override
        public MessageDetailModel[] newArray(int size) {
            return new MessageDetailModel[size];
        }
    };

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public static Creator<MessageDetailModel> getCREATOR() {
        return CREATOR;
    }
}