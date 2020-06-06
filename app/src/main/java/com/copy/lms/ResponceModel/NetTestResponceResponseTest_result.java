package com.copy.lms.ResponceModel;

import java.util.ArrayList;

public class NetTestResponceResponseTest_result {
    private NetTestResponceResponseTest_resultScore score;
    private String result_id;

    public String getResult_id() {
        return result_id;
    }

    public void setResult_id(String result_id) {
        this.result_id = result_id;
    }

    private ArrayList<NetTestResponceResponseTest_resultAnswers> answers;

    public NetTestResponceResponseTest_resultScore getScore() {
        return this.score;
    }

    public void setScore(NetTestResponceResponseTest_resultScore score) {
        this.score = score;
    }

    public ArrayList<NetTestResponceResponseTest_resultAnswers> getAnswers() {
        return this.answers;
    }

    public void setAnswers(ArrayList<NetTestResponceResponseTest_resultAnswers> answers) {
        this.answers = answers;
    }
}
