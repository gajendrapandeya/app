package com.copy.lms.ResponceModel;

import java.util.ArrayList;

public class NetBundleDetailDataResult {
    private ArrayList<NetBundleDetailDataResultCourses> courses;
    private NetBundleDetailDataResultBundle bundle;

    public ArrayList<NetBundleDetailDataResultCourses> getCourses() {
        return this.courses;
    }

    public void setCourses(ArrayList<NetBundleDetailDataResultCourses> courses) {
        this.courses = courses;
    }

    public NetBundleDetailDataResultBundle getBundle() {
        return this.bundle;
    }

    public void setBundle(NetBundleDetailDataResultBundle bundle) {
        this.bundle = bundle;
    }
}
