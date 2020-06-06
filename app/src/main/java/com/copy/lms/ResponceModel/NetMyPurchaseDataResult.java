package com.copy.lms.ResponceModel;

import java.util.ArrayList;

public class NetMyPurchaseDataResult {
    private ArrayList<NetMyPurchaseDataResultCourses> courses;
    private ArrayList<NetMyPurchaseDataResultBundles> bundles;

    public ArrayList<NetMyPurchaseDataResultCourses> getCourses() {
        return this.courses;
    }

    public void setCourses(ArrayList<NetMyPurchaseDataResultCourses> courses) {
        this.courses = courses;
    }

    public ArrayList<NetMyPurchaseDataResultBundles> getBundles() {
        return this.bundles;
    }

    public void setBundles(ArrayList<NetMyPurchaseDataResultBundles> bundles) {
        this.bundles = bundles;
    }
}
