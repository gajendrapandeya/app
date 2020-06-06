package com.copy.lms.ResponceModel;

import java.util.ArrayList;

public class NetSignUpData {
    private ArrayList<NetSignUpDataFields> fields;
    private String status;

    public ArrayList<NetSignUpDataFields> getFields() {
        return this.fields;
    }

    public void setFields(ArrayList<NetSignUpDataFields> fields) {
        this.fields = fields;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
