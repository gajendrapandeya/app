package com.copy.lms.model;

import androidx.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;


public class SponserModel extends BaseObservable implements Parcelable {



    public SponserModel() {
    }
    private String image;
    private String updated_at;
    private String name;
    private String link;
    private String logo;
    private String created_at;
    private int id;
    private int status;

    protected SponserModel(Parcel in) {
        image = in.readString();
        updated_at = in.readString();
        name = in.readString();
        link = in.readString();
        logo = in.readString();
        created_at = in.readString();
        id = in.readInt();
        status = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(updated_at);
        dest.writeString(name);
        dest.writeString(link);
        dest.writeString(logo);
        dest.writeString(created_at);
        dest.writeInt(id);
        dest.writeInt(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SponserModel> CREATOR = new Creator<SponserModel>() {
        @Override
        public SponserModel createFromParcel(Parcel in) {
            return new SponserModel(in);
        }

        @Override
        public SponserModel[] newArray(int size) {
            return new SponserModel[size];
        }
    };

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}