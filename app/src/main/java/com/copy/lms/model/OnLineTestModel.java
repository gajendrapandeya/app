package com.copy.lms.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.copy.lms.ResponceModel.NetTestResponceResponseTestQuestions;
import com.copy.lms.ResponceModel.NetTestResponceResponseTest_resultAnswers;

import java.util.ArrayList;

import androidx.databinding.BaseObservable;

public class OnLineTestModel extends BaseObservable implements Parcelable {

    private int course_id;
    private String updated_at;
    private ArrayList<NetTestResponceResponseTestQuestions> questions;
    private ArrayList<NetTestResponceResponseTest_resultAnswers> responseTestResultAnswers;
    private String description;
    private String created_at;
    private String id;
    private int published;
    private String title;
    private String lesson_id;
    private String deleted_at;
    private String slug;
    private String testId;
    private String questionId;
    private String answerId;

    protected OnLineTestModel(Parcel in) {
        course_id = in.readInt();
        updated_at = in.readString();
        description = in.readString();
        created_at = in.readString();
        id = in.readString();
        published = in.readInt();
        title = in.readString();
        lesson_id = in.readString();
        deleted_at = in.readString();
        slug = in.readString();
        testId = in.readString();
        questionId = in.readString();
        answerId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(course_id);
        dest.writeString(updated_at);
        dest.writeString(description);
        dest.writeString(created_at);
        dest.writeString(id);
        dest.writeInt(published);
        dest.writeString(title);
        dest.writeString(lesson_id);
        dest.writeString(deleted_at);
        dest.writeString(slug);
        dest.writeString(testId);
        dest.writeString(questionId);
        dest.writeString(answerId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OnLineTestModel> CREATOR = new Creator<OnLineTestModel>() {
        @Override
        public OnLineTestModel createFromParcel(Parcel in) {
            return new OnLineTestModel(in);
        }

        @Override
        public OnLineTestModel[] newArray(int size) {
            return new OnLineTestModel[size];
        }
    };

    public ArrayList<NetTestResponceResponseTest_resultAnswers> getResponseTestResultAnswers() {
        return responseTestResultAnswers;
    }

    public void setResponseTestResultAnswers(ArrayList<NetTestResponceResponseTest_resultAnswers> responseTestResultAnswers) {
        this.responseTestResultAnswers = responseTestResultAnswers;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

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

    public String getId() {
        return this.id;
    }

    public void setId(String  id) {
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
    public OnLineTestModel(){

    }



}