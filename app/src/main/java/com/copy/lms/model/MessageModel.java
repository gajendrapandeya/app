package com.copy.lms.model;

import androidx.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import com.copy.lms.ResponceModel.NetMessageDataThreadsPivot;

import java.util.ArrayList;


public class MessageModel extends BaseObservable implements Parcelable {
    private ArrayList<MessagChatModel> messages;

    public MessageModel() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MessageModel> CREATOR = new Creator<MessageModel>() {
        @Override
        public MessageModel createFromParcel(Parcel in) {
            return new MessageModel(in);
        }

        @Override
        public MessageModel[] newArray(int size) {
            return new MessageModel[size];
        }
    };

    public ArrayList<MessagChatModel> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MessagChatModel> messages) {
        this.messages = messages;
    }

    private NetMessageDataThreadsPivot pivot;
    private String id;

    protected MessageModel(Parcel in) {
        id = in.readString();
    }


    public NetMessageDataThreadsPivot getPivot() {
        return this.pivot;
    }

    public void setPivot(NetMessageDataThreadsPivot pivot) {
        this.pivot = pivot;
    }


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}