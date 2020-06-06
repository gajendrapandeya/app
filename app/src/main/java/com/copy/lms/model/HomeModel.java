package com.copy.lms.model;

import androidx.databinding.BaseObservable;

import android.os.Parcel;
import android.os.Parcelable;


public class HomeModel extends BaseObservable implements Parcelable {

    private String fName, lName;
    private int image;
    private String  id;

    public HomeModel() {
    }


    protected HomeModel(Parcel in) {
        fName = in.readString();
        lName = in.readString();
        image = in.readInt();
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fName);
        dest.writeString(lName);
        dest.writeInt(image);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HomeModel> CREATOR = new Creator<HomeModel>() {
        @Override
        public HomeModel createFromParcel(Parcel in) {
            return new HomeModel(in);
        }

        @Override
        public HomeModel[] newArray(int size) {
            return new HomeModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public static Creator<HomeModel> getCREATOR() {
        return CREATOR;
    }
}