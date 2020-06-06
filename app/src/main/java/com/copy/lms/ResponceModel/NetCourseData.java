package com.copy.lms.ResponceModel;

public class NetCourseData {
    private NetCourseDataResult result;
    private String type;
    private String status;

    public NetCourseDataResult getResult() {
        return this.result;
    }

    public void setResult(NetCourseDataResult result) {
        this.result = result;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
