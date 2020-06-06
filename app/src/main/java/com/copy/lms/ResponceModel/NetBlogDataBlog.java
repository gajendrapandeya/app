package com.copy.lms.ResponceModel;

import java.util.ArrayList;

public class NetBlogDataBlog {
    private String first_page_url;
    private String path;
    private int per_page;
    private int total;
    private ArrayList<NetBlogDataBlogData> data;
    private int last_page;
    private String last_page_url;
    private String next_page_url;
    private int from;
    private int to;
    private String prev_page_url;
    private int current_page;

    public String getFirst_page_url() {
        return this.first_page_url;
    }

    public void setFirst_page_url(String first_page_url) {
        this.first_page_url = first_page_url;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPer_page() {
        return this.per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<NetBlogDataBlogData> getData() {
        return this.data;
    }

    public void setData(ArrayList<NetBlogDataBlogData> data) {
        this.data = data;
    }

    public int getLast_page() {
        return this.last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public String getLast_page_url() {
        return this.last_page_url;
    }

    public void setLast_page_url(String last_page_url) {
        this.last_page_url = last_page_url;
    }

    public String getNext_page_url() {
        return this.next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public int getFrom() {
        return this.from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return this.to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getPrev_page_url() {
        return this.prev_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public int getCurrent_page() {
        return this.current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }
}
