package com.copy.lms.model;

import android.graphics.drawable.Drawable;

import com.copy.lms.ResponceModel.NetTestResponceResponseTestQuestions;
import com.copy.lms.ResponceModel.NetTestResponceResponseTestQuestionsOptions;
import com.copy.lms.ResponceModel.NetTestResponceResponseTest_resultAnswers;
import com.copy.lms.ResponceModel.NetTestResponceResponseTest_resultScore;

import java.util.ArrayList;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class OnlineTestListModel extends BaseObservable {
    private String noDataLable, noDataMessage, noDataButtonText;
    private ArrayList<OnLineTestModel> arrayList;
    private ArrayList<NetTestResponceResponseTest_resultScore> responceResponseTest_resultScores;
    private ArrayList<NetTestResponceResponseTest_resultAnswers> responseTestResultAnswers;
    private ArrayList<NetTestResponceResponseTestQuestions> responceResponseTestQuestions;
    private ArrayList<NetTestResponceResponseTestQuestionsOptions> responceResponseTestQuestionsOptions;
    private boolean apiCallActive = true;
    private Drawable noDataImage;

    public OnlineTestListModel() {
        responceResponseTestQuestions = new ArrayList<>();
    }

    public ArrayList<NetTestResponceResponseTest_resultAnswers> getResponseTestResultAnswers() {
        return responseTestResultAnswers;
    }

    public void setResponseTestResultAnswers(ArrayList<NetTestResponceResponseTest_resultAnswers> responseTestResultAnswers) {
        this.responseTestResultAnswers = responseTestResultAnswers;
    }

    public ArrayList<NetTestResponceResponseTest_resultScore> getResponceResponseTest_resultScores() {
        return responceResponseTest_resultScores;
    }

    public void setResponceResponseTest_resultScores(ArrayList<NetTestResponceResponseTest_resultScore> responceResponseTest_resultScores) {
        this.responceResponseTest_resultScores = responceResponseTest_resultScores;
    }

    @Bindable
    public boolean isApiCallActive() {
        return apiCallActive;
    }

    public void setApiCallActive(boolean apiCallActive) {
        this.apiCallActive = apiCallActive;
        notifyChange();
    }

    public String getNoDataButtonText() {
        return noDataButtonText;
    }

    public void setNoDataButtonText(String noDataButtonText) {
        this.noDataButtonText = noDataButtonText;
    }

    public Drawable getNoDataImage() {
        return noDataImage;
    }

    public void setNoDataImage(Drawable noDataImage) {
        this.noDataImage = noDataImage;
    }

    public String getNoDataLable() {
        return noDataLable;
    }

    public void setNoDataLable(String noDataLable) {
        this.noDataLable = noDataLable;
    }

    public String getNoDataMessage() {
        return noDataMessage;
    }

    public void setNoDataMessage(String noDataMessage) {
        this.noDataMessage = noDataMessage;
    }

    @Bindable
    public ArrayList<OnLineTestModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<OnLineTestModel> arrayList) {
        this.arrayList = arrayList;
        notifyChange();
    }

    public ArrayList<NetTestResponceResponseTestQuestions> getResponceResponseTestQuestions() {
        return responceResponseTestQuestions;
    }

    public void setResponceResponseTestQuestions(ArrayList<NetTestResponceResponseTestQuestions> responceResponseTestQuestions) {
        this.responceResponseTestQuestions = responceResponseTestQuestions;
    }

    public void setResponceResponseTestQuestionsOptions(ArrayList<NetTestResponceResponseTestQuestionsOptions> responceResponseTestQuestionsOptions) {
        this.responceResponseTestQuestionsOptions = responceResponseTestQuestionsOptions;
    }

    public ArrayList<NetTestResponceResponseTestQuestionsOptions> getResponceResponseTestQuestionsOptions() {
        return responceResponseTestQuestionsOptions;
    }
}
