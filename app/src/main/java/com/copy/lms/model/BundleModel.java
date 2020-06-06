package com.copy.lms.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;


public class BundleModel extends BaseObservable implements Parcelable {

    private int trending;
    private String image;
    private int featured;
    private String meta_title;
    private String description;
    private String created_at;
    private int published;
    private String title;
    private String meta_keywords;
    private String course_image;
    private String deleted_at;
    private String meta_description;
    private int category_id;
    private String updated_at;
    private String price;
    private int id;
    private int popular;
    private String slug;
    private String start_date;
    private String free;

    protected BundleModel(Parcel in) {
        trending = in.readInt();
        image = in.readString();
        featured = in.readInt();
        meta_title = in.readString();
        description = in.readString();
        created_at = in.readString();
        published = in.readInt();
        title = in.readString();
        meta_keywords = in.readString();
        course_image = in.readString();
        deleted_at = in.readString();
        meta_description = in.readString();
        category_id = in.readInt();
        updated_at = in.readString();
        price = in.readString();
        id = in.readInt();
        popular = in.readInt();
        slug = in.readString();
        start_date = in.readString();
        free = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(trending);
        dest.writeString(image);
        dest.writeInt(featured);
        dest.writeString(meta_title);
        dest.writeString(description);
        dest.writeString(created_at);
        dest.writeInt(published);
        dest.writeString(title);
        dest.writeString(meta_keywords);
        dest.writeString(course_image);
        dest.writeString(deleted_at);
        dest.writeString(meta_description);
        dest.writeInt(category_id);
        dest.writeString(updated_at);
        dest.writeString(price);
        dest.writeInt(id);
        dest.writeInt(popular);
        dest.writeString(slug);
        dest.writeString(start_date);
        dest.writeString(free);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BundleModel> CREATOR = new Creator<BundleModel>() {
        @Override
        public BundleModel createFromParcel(Parcel in) {
            return new BundleModel(in);
        }

        @Override
        public BundleModel[] newArray(int size) {
            return new BundleModel[size];
        }
    };

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }
     public BundleModel(){}



    public int getTrending() {
        return this.trending;
    }

    public void setTrending(int trending) {
        this.trending = trending;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getFeatured() {
        return this.featured;
    }

    public void setFeatured(int featured) {
        this.featured = featured;
    }

    public String getMeta_title() {
        return this.meta_title;
    }

    public void setMeta_title(String meta_title) {
        this.meta_title = meta_title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getPublished() {
        return this.published;
    }

    public void setPublished(int published) {
        this.published = published;
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

    public String getCourse_image() {
        return this.course_image;
    }

    public void setCourse_image(String course_image) {
        this.course_image = course_image;
    }

    public String getDeleted_at() {
        return this.deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
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

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPopular() {
        return this.popular;
    }

    public void setPopular(int popular) {
        this.popular = popular;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getStart_date() {
        return this.start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }}