package com.copy.lms.ResponceModel;

public class NetBlogDetailData {
    private int next;
    private int previous;
    private NetBlogDetailDataBlog blog;
    private String status;

    public int getNext() {
        return this.next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getPrevious() {
        return this.previous;
    }

    public void setPrevious(int previous) {
        this.previous = previous;
    }

    public NetBlogDetailDataBlog getBlog() {
        return this.blog;
    }

    public void setBlog(NetBlogDetailDataBlog blog) {
        this.blog = blog;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
