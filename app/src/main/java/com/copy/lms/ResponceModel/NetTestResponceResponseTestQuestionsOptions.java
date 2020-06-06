package com.copy.lms.ResponceModel;

public class NetTestResponceResponseTestQuestionsOptions {
    private int correct;
    private String updated_at;
    private String option_text;
    private String created_at;
    private String id;
    private String explanation;
    private String question_id;
    private String deleted_at;
    private  boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getCorrect() {
        return this.correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getOption_text() {
        return this.option_text;
    }

    public void setOption_text(String option_text) {
        this.option_text = option_text;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExplanation() {
        return this.explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getQuestion_id() {
        return this.question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getDeleted_at() {
        return this.deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }
}
