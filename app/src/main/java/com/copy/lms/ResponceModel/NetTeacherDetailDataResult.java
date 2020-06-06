package com.copy.lms.ResponceModel;

import java.util.ArrayList;

public class NetTeacherDetailDataResult {
    private ArrayList<NetTeacherDetailDataResultCourses> courses;
    private NetTeacherDetailDataResultTeacher teacher;
    private ArrayList<NetTeacherDetailDataResultBundles> bundles;

    public ArrayList<NetTeacherDetailDataResultCourses> getCourses() {
        return this.courses;
    }

    public void setCourses(ArrayList<NetTeacherDetailDataResultCourses> courses) {
        this.courses = courses;
    }

    public NetTeacherDetailDataResultTeacher getTeacher() {
        return this.teacher;
    }

    public void setTeacher(NetTeacherDetailDataResultTeacher teacher) {
        this.teacher = teacher;
    }

    public ArrayList<NetTeacherDetailDataResultBundles> getBundles() {
        return this.bundles;
    }

    public void setBundles(ArrayList<NetTeacherDetailDataResultBundles> bundles) {
        this.bundles = bundles;
    }
}
