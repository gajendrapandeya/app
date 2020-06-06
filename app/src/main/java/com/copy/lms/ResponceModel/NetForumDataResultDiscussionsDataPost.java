package com.copy.lms.ResponceModel;

import android.os.Parcel;
import android.os.Parcelable;

public class NetForumDataResultDiscussionsDataPost  implements Parcelable {
    private int chatter_discussion_id;
    private String updated_at;
    private int user_id;
    private int markdown;
    private String created_at;
    private int id;
    private String body;
    private int locked;
    private String deleted_at;
    private String user_name;
    private String user_image;
    private NetForumDataResultDiscussionsDataPostUser user;


    protected NetForumDataResultDiscussionsDataPost(Parcel in) {
        chatter_discussion_id = in.readInt();
        updated_at = in.readString();
        user_id = in.readInt();
        markdown = in.readInt();
        created_at = in.readString();
        id = in.readInt();
        body = in.readString();
        locked = in.readInt();
        deleted_at = in.readString();
        user_name = in.readString();
        user_image = in.readString();
        user = in.readParcelable(NetForumDataResultDiscussionsDataPostUser.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(chatter_discussion_id);
        dest.writeString(updated_at);
        dest.writeInt(user_id);
        dest.writeInt(markdown);
        dest.writeString(created_at);
        dest.writeInt(id);
        dest.writeString(body);
        dest.writeInt(locked);
        dest.writeString(deleted_at);
        dest.writeString(user_name);
        dest.writeString(user_image);
        dest.writeParcelable(user, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NetForumDataResultDiscussionsDataPost> CREATOR = new Creator<NetForumDataResultDiscussionsDataPost>() {
        @Override
        public NetForumDataResultDiscussionsDataPost createFromParcel(Parcel in) {
            return new NetForumDataResultDiscussionsDataPost(in);
        }

        @Override
        public NetForumDataResultDiscussionsDataPost[] newArray(int size) {
            return new NetForumDataResultDiscussionsDataPost[size];
        }
    };

    public int getChatter_discussion_id() {
        return this.chatter_discussion_id;
    }

    public void setChatter_discussion_id(int chatter_discussion_id) {
        this.chatter_discussion_id = chatter_discussion_id;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getMarkdown() {
        return this.markdown;
    }

    public void setMarkdown(int markdown) {
        this.markdown = markdown;
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

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getLocked() {
        return this.locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public String getDeleted_at() {
        return this.deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public NetForumDataResultDiscussionsDataPostUser getUser() {
        return this.user;
    }

    public void setUser(NetForumDataResultDiscussionsDataPostUser user) {
        this.user = user;
        this.user_name =user.getFirst_name();
        this.user_image = user.getImage();

    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user.getFirst_name();
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user.getImage();
    }
}
