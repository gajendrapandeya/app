package com.copy.lms.ResponceModel;

import java.util.ArrayList;

public class NetCartListResult {
    private ArrayList<NetCartListResultCourses> courses;
    private double total;
    private ArrayList<NetCartListResultBundles> bundles;

    public ArrayList<NetCartListResultCourses> getCourses() {
        return this.courses;
    }

    public void setCourses(ArrayList<NetCartListResultCourses> courses) {
        this.courses = courses;
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<NetCartListResultBundles> getBundles() {
        return this.bundles;
    }

    public void setBundles(ArrayList<NetCartListResultBundles> bundles) {
        this.bundles = bundles;
    }
}
