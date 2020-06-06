package com.copy.lms.model;

import androidx.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;


public class WishModel extends BaseObservable implements Parcelable {

    private String fName, lName;
    private int image;

    public WishModel() {
    }

    protected WishModel(Parcel in) {
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

    public static final Creator<WishModel> CREATOR = new Creator<WishModel>() {
        @Override
        public WishModel createFromParcel(Parcel in) {
            return new WishModel(in);
        }

        @Override
        public WishModel[] newArray(int size) {
            return new WishModel[size];
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

    public static Creator<WishModel> getCREATOR() {
        return CREATOR;
    }
}