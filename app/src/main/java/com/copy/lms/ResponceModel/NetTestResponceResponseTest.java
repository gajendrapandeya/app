package com.copy.lms.ResponceModel;

import java.util.ArrayList;

public class NetTestResponceResponseTest {
    private int course_id;
    private String updated_at;
    private ArrayList<NetTestResponceResponseTestQuestions> questions;
    private String description;
    private String created_at;
    private int id;
    private int published;
    private String title;
    private String lesson_id;
    private String deleted_at;
    private String slug;

    public int getCourse_id() {
        return this.course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public ArrayList<NetTestResponceResponseTestQuestions> getQuestions() {
        return this.questions;
    }

    public void setQuestions(ArrayList<NetTestResponceResponseTestQuestions> questions) {
        this.questions = questions;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublished() {
        return this.published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLesson_id() {
        return this.lesson_id;
    }

    public void setLesson_id(String lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String getDeleted_at() {
        return this.deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
