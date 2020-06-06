package com.copy.lms.ResponceModel;

import java.util.ArrayList;

public class NetForumDataResultDiscussionsData {
    private int answered;
    private String color;
    private String created_at;
    private String title;
    private String deleted_at;
    private int chatter_category_id;
    private String last_reply_at;
    private String updated_at;
    private ArrayList<NetForumDataResultDiscussionsDataPost> post;
    private int user_id;
    private int sticky;
    private int id;
    private NetForumDataResultDiscussionsDataCategory category;
    private NetForumDataResultDiscussionsDataUser user;
    private int views;
    private String slug;
    private NetForumDataResultDiscussionsDataPosts_count[] posts_count;

    public int getAnswered() {
        return this.answered;
    }

    public void setAnswered(int answered) {
        this.answered = answered;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeleted_at() {
        return this.deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public int getChatter_category_id() {
        return this.chatter_category_id;
    }

    public void setChatter_category_id(int chatter_category_id) {
        this.chatter_category_id = chatter_category_id;
    }

    public String getLast_reply_at() {
        return this.last_reply_at;
    }

    public void setLast_reply_at(String last_reply_at) {
        this.last_reply_at = last_reply_at;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public ArrayList<NetForumDataResultDiscussionsDataPost> getPost() {
        return this.post;
    }

    public void setPost(ArrayList<NetForumDataResultDiscussionsDataPost> post) {
        this.post = post;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSticky() {
        return this.sticky;
    }

    public void setSticky(int sticky) {
        this.sticky = sticky;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NetForumDataResultDiscussionsDataCategory getCategory() {
        return this.category;
    }

    public void setCategory(NetForumDataResultDiscussionsDataCategory category) {
        this.category = category;
    }

    public NetForumDataResultDiscussionsDataUser getUser() {
        return this.user;
    }

    public void setUser(NetForumDataResultDiscussionsDataUser user) {
        this.user = user;
    }

    public int getViews() {
        return this.views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public NetForumDataResultDiscussionsDataPosts_count[] getPosts_count() {
        return this.posts_count;
    }

    public void setPosts_count(NetForumDataResultDiscussionsDataPosts_count[] posts_count) {
        this.posts_count = posts_count;
    }
}
