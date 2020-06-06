package com.copy.lms.model;

import androidx.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;


public class FaqModel extends BaseObservable implements Parcelable {

    private int category_id;
    private String question;
    private String answer;
    private String updated_at;
    private String created_at;
    private int id;
    private int status;

    protected FaqModel(Parcel in) {
        category_id = in.readInt();
        question = in.readString();
        answer = in.readString();
        updated_at = in.readString();
        created_at = in.readString();
        id = in.readInt();
        status = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(category_id);
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeString(updated_at);
        dest.writeString(created_at);
        dest.writeInt(id);
        dest.writeInt(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FaqModel> CREATOR = new Creator<FaqModel>() {
        @Override
        public FaqModel createFromParcel(Parcel in) {
            return new FaqModel(in);
        }

        @Override
        public FaqModel[] newArray(int size) {
            return new FaqModel[size];
        }
    };

    public int getCategory_id() {
        return this.category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
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
    public FaqModel() {
    }


   }