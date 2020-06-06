package com.copy.lms.model;

import androidx.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import com.copy.lms.ResponceModel.NetBlogDataBlogDataAuthor;
import com.copy.lms.ResponceModel.NetBlogDataBlogDataCategory;


public class BlogModel extends BaseObservable implements Parcelable {



    public BlogModel() {
    }
    private String image;
    private String blog_author;
    private String blog_category;
    private String meta_title;
    private NetBlogDataBlogDataAuthor author;
    private String created_at;
    private String blog_image;
    private String title;
    private String meta_keywords;
    private String deleted_at;
    private String content;
    private String meta_description;
    private int category_id;
    private String updated_at;
    private int user_id;
    private int id;
    private NetBlogDataBlogDataCategory category;
    private String slug;
    private int views;

    protected BlogModel(Parcel in) {
        image = in.readString();
        blog_author = in.readString();
        blog_category = in.readString();
        meta_title = in.readString();
        created_at = in.readString();
        blog_image = in.readString();
        title = in.readString();
        meta_keywords = in.readString();
        deleted_at = in.readString();
        content = in.readString();
        meta_description = in.readString();
        category_id = in.readInt();
        updated_at = in.readString();
        user_id = in.readInt();
        id = in.readInt();
        slug = in.readString();
        views = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(blog_author);
        dest.writeString(blog_category);
        dest.writeString(meta_title);
        dest.writeString(created_at);
        dest.writeString(blog_image);
        dest.writeString(title);
        dest.writeString(meta_keywords);
        dest.writeString(deleted_at);
        dest.writeString(content);
        dest.writeString(meta_description);
        dest.writeInt(category_id);
        dest.writeString(updated_at);
        dest.writeInt(user_id);
        dest.writeInt(id);
        dest.writeString(slug);
        dest.writeInt(views);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BlogModel> CREATOR = new Creator<BlogModel>() {
        @Override
        public BlogModel createFromParcel(Parcel in) {
            return new BlogModel(in);
        }

        @Override
        public BlogModel[] newArray(int size) {
            return new BlogModel[size];
        }
    };

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBlog_author() {
        return this.blog_author;
    }

    public void setBlog_author(String blog_author) {
        this.blog_author = blog_author;
    }

    public String getBlog_category() {
        return this.blog_category;
    }

    public void setBlog_category(String blog_category) {
        this.blog_category = blog_category;
    }

    public String getMeta_title() {
        return this.meta_title;
    }

    public void setMeta_title(String meta_title) {
        this.meta_title = meta_title;
    }

    public NetBlogDataBlogDataAuthor getAuthor() {
        return this.author;
    }

    public void setAuthor(NetBlogDataBlogDataAuthor author) {
        this.author = author;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getBlog_image() {
        return this.blog_image;
    }

    public void setBlog_image(String blog_image) {
        this.blog_image = blog_image;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMeta_keywords() {
        return this.meta_keywords;
    }

    public void setMeta_keywords(String meta_keywords) {
        this.meta_keywords = meta_keywords;
    }

    public String getDeleted_at() {
        return this.deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMeta_description() {
        return this.meta_description;
    }

    public void setMeta_description(String meta_description) {
        this.meta_description = meta_description;
    }

    public int getCategory_id() {
        return this.category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NetBlogDataBlogDataCategory getCategory() {
        return this.category;
    }

    public void setCategory(NetBlogDataBlogDataCategory category) {
        this.category = category;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getViews() {
        return this.views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}