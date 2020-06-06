package com.copy.lms.ResponceModel;

import java.util.ArrayList;

public class NetTestResponceResponseTestQuestions {
    private int score;
    private String question;
    private String updated_at;
    private int user_id;
    private String question_image;
    private ArrayList<NetTestResponceResponseTestQuestionsOptions> options;
    private String created_at;
    private NetTestResponceResponseTestQuestionsPivot pivot;
    private String id;
    private String deleted_at;


    public NetTestResponceResponseTestQuestions(){
        options = new ArrayList<>();
    }
    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getQuestion_image() {
        return this.question_image;
    }

    public void setQuestion_image(String question_image) {
        this.question_image = question_image;
    }

    public ArrayList<NetTestResponceResponseTestQuestionsOptions> getOptions() {
        return this.options;
    }

    public void setOptions(ArrayList<NetTestResponceResponseTestQuestionsOptions> options) {
        this.options = options;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public NetTestResponceResponseTestQuestionsPivot getPivot() {
        return this.pivot;
    }

    public void setPivot(NetTestResponceResponseTestQuestionsPivot pivot) {
        this.pivot = pivot;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeleted_at() {
        return this.deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }
}
