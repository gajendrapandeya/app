package com.copy.lms.ResponceModel;

import java.util.ArrayList;

public class NetSingleCourseDataResult {
    private int total_ratings;
    private boolean is_reviewed;
    private ArrayList<NetSingleCourseDataResultCourse_timeline> course_timeline;
    private boolean is_certified;
    private boolean purchased_course;
    private int course_rating;
    private String course_video;
    private NetSingleCourseDataResultCourse course;
    private int[] completed_lessons;
    private int course_process;
    private NetSingleCourseDataResultLessons[] lessons;
    private NetSingleCourseDataResultContinue_course continue_course;

    public int getTotal_ratings() {
        return this.total_ratings;
    }

    public void setTotal_ratings(int total_ratings) {
        this.total_ratings = total_ratings;
    }

    public boolean getIs_reviewed() {
        return this.is_reviewed;
    }

    public void setIs_reviewed(boolean is_reviewed) {
        this.is_reviewed = is_reviewed;
    }

    public ArrayList<NetSingleCourseDataResultCourse_timeline> getCourse_timeline() {
        return this.course_timeline;
    }

    public void setCourse_timeline(ArrayList<NetSingleCourseDataResultCourse_timeline> course_timeline) {
        this.course_timeline = course_timeline;
    }

    public boolean getIs_certified() {
        return this.is_certified;
    }

    public void setIs_certified(boolean is_certified) {
        this.is_certified = is_certified;
    }

    public boolean getPurchased_course() {
        return this.purchased_course;
    }

    public void setPurchased_course(boolean purchased_course) {
        this.purchased_course = purchased_course;
    }

    public int getCourse_rating() {
        return this.course_rating;
    }

    public void setCourse_rating(int course_rating) {
        this.course_rating = course_rating;
    }

    public String getCourse_video() {
        return this.course_video;
    }

    public void setCourse_video(String course_video) {
        this.course_video = course_video;
    }

    public NetSingleCourseDataResultCourse getCourse() {
        return this.course;
    }

    public void setCourse(NetSingleCourseDataResultCourse course) {
        this.course = course;
    }

    public int[] getCompleted_lessons() {
        return this.completed_lessons;
    }

    public void setCompleted_lessons(int[] completed_lessons) {
        this.completed_lessons = completed_lessons;
    }

    public int getCourse_process() {
        return this.course_process;
    }

    public void setCourse_process(int course_process) {
        this.course_process = course_process;
    }

    public NetSingleCourseDataResultLessons[] getLessons() {
        return this.lessons;
    }

    public void setLessons(NetSingleCourseDataResultLessons[] lessons) {
        this.lessons = lessons;
    }

    public NetSingleCourseDataResultContinue_course getContinue_course() {
        return this.continue_course;
    }

    public void setContinue_course(NetSingleCourseDataResultContinue_course continue_course) {
        this.continue_course = continue_course;
    }
}
