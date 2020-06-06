package com.copy.lms.model;

import android.os.Parcel;
import android.os.Parcelable;


public class ContactUsModel implements Parcelable {

    private String mobile_brand, mobile_model, os, name, email, subject;


    String data,message;
    boolean status;
    public ContactUsModel(){

    }


    protected ContactUsModel(Parcel in) {
        mobile_brand = in.readString();
        mobile_model = in.readString();
        os = in.readString();
        name = in.readString();
        email = in.readString();
        subject = in.readString();
        data = in.readString();
        message = in.readString();
        status = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mobile_brand);
        dest.writeString(mobile_model);
        dest.writeString(os);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(subject);
        dest.writeString(data);
        dest.writeString(message);
        dest.writeByte((byte) (status ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContactUsModel> CREATOR = new Creator<ContactUsModel>() {
        @Override
        public ContactUsModel createFromParcel(Parcel in) {
            return new ContactUsModel(in);
        }

        @Override
        public ContactUsModel[] newArray(int size) {
            return new ContactUsModel[size];
        }
    };


    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMobile_brand() {
        return mobile_brand;
    }

    public void setMobile_brand(String mobile_brand) {
        this.mobile_brand = mobile_brand;
    }

    public String getMobile_model() {
        return mobile_model;
    }

    public void setMobile_model(String mobile_model) {
        this.mobile_model = mobile_model;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
