package com.copy.lms.ResponceModel;

import java.util.ArrayList;

public class NetMessageData {
    private ArrayList<NetMessageDataTeachers> teachers;
    private ArrayList<NetMessageDataThreads> threads;
    private String status;

    public ArrayList<NetMessageDataTeachers> getTeachers() {
        return this.teachers;
    }

    public void setTeachers(ArrayList<NetMessageDataTeachers> teachers) {
        this.teachers = teachers;
    }

    public ArrayList<NetMessageDataThreads> getThreads() {
        return this.threads;
    }

    public void setThreads(ArrayList<NetMessageDataThreads> threads) {
        this.threads = threads;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
