package com.copy.lms.ResponceModel;

public class NetSingleCourseDataResultLessons {
    private int course_id;
    private int sequence;
    private String updated_at;
    private String model_type;
    private String created_at;
    private int id;
    private int model_id;

    public int getCourse_id() {
        return this.course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getSequence() {
        return this.sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getModel_type() {
        return this.model_type;
    }

    public void setModel_type(String model_type) {
        this.model_type = model_type;
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

    public int getModel_id() {
        return this.model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }
}
