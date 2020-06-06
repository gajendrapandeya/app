package com.copy.lms.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;


public class CertificateModel extends BaseObservable implements Parcelable {



    public CertificateModel() {
    }
    private String course_id;
    private String updated_at;
    private String user_id;
    private String name;
    private String certificate_link;
    private String created_at;
    private String id;
    private String url;
    private int status;

    protected CertificateModel(Parcel in) {
        course_id = in.readString();
        updated_at = in.readString();
        user_id = in.readString();
        name = in.readString();
        certificate_link = in.readString();
        created_at = in.readString();
        id = in.readString();
        url = in.readString();
        status = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(course_id);
        dest.writeString(updated_at);
        dest.writeString(user_id);
        dest.writeString(name);
        dest.writeString(certificate_link);
        dest.writeString(created_at);
        dest.writeString(id);
        dest.writeString(url);
        dest.writeInt(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CertificateModel> CREATOR = new Creator<CertificateModel>() {
        @Override
        public CertificateModel createFromParcel(Parcel in) {
            return new CertificateModel(in);
        }

        @Override
        public CertificateModel[] newArray(int size) {
            return new CertificateModel[size];
        }
    };

    public String getCourse_id() {
        return this.course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertificate_link() {
        return this.certificate_link;
    }

    public void setCertificate_link(String certificate_link) {
        this.certificate_link = certificate_link;
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

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }}