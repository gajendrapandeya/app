package com.copy.lms.model;




import android.os.Parcel;
import android.os.Parcelable;

import com.copy.lms.ResponceModel.NetMessageDataThreadsMessagesSender;

import androidx.databinding.BaseObservable;


public class MessagChatModel extends BaseObservable implements Parcelable {
    private String thread_id;
    private String created_at;
    private String id;
    private String body;
    private String sender_id;

    private NetMessageDataThreadsMessagesSender sender;

    public MessagChatModel(){

    }

    protected MessagChatModel(Parcel in) {
        thread_id = in.readString();
        created_at = in.readString();
        id = in.readString();
        body = in.readString();
        sender_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(thread_id);
        dest.writeString(created_at);
        dest.writeString(id);
        dest.writeString(body);
        dest.writeString(sender_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MessagChatModel> CREATOR = new Creator<MessagChatModel>() {
        @Override
        public MessagChatModel createFromParcel(Parcel in) {
            return new MessagChatModel(in);
        }

        @Override
        public MessagChatModel[] newArray(int size) {
            return new MessagChatModel[size];
        }
    };

    public NetMessageDataThreadsMessagesSender getSender() {
        return sender;
    }

    public void setSender(NetMessageDataThreadsMessagesSender sender) {
        this.sender = sender;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }


    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }
}