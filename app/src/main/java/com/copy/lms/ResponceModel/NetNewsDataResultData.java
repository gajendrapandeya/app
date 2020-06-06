package com.copy.lms.ResponceModel;

public class NetNewsDataResultData {
    private String image;
    private String blog_author;
    private String blog_category;
    private int category_id;
    private int user_id;
    private NetNewsDataResultDataAuthor author;
    private String blog_image;
    private int id;
    private String title;
    private NetNewsDataResultDataCategory category;
    private String slug;
    private String content;

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

    public int getCategory_id() {
        return this.category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public NetNewsDataResultDataAuthor getAuthor() {
        return this.author;
    }

    public void setAuthor(NetNewsDataResultDataAuthor author) {
        this.author = author;
    }

    public String getBlog_image() {
        return this.blog_image;
    }

    public void setBlog_image(String blog_image) {
        this.blog_image = blog_image;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NetNewsDataResultDataCategory getCategory() {
        return this.category;
    }

    public void setCategory(NetNewsDataResultDataCategory category) {
        this.category = category;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
