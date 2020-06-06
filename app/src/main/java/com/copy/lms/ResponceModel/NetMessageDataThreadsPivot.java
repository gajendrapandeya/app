package com.copy.lms.ResponceModel;

public class NetMessageDataThreadsPivot {
    private Object last_read;
    private int thread_id;
    private int user_id;

    public Object getLast_read() {
        return this.last_read;
    }

    public void setLast_read(Object last_read) {
        this.last_read = last_read;
    }

    public int getThread_id() {
        return this.thread_id;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
