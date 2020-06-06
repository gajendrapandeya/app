package com.copy.lms.ResponceModel;

import java.util.ArrayList;

public class NetCertificatModel {
    private ArrayList<NetCertificatModelResult> result;
    private String status;

    public ArrayList<NetCertificatModelResult> getResult() {
        return this.result;
    }

    public void setResult(ArrayList<NetCertificatModelResult> result) {
        this.result = result;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
