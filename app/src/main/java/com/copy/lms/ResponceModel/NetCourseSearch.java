package com.copy.lms.ResponceModel;

public class NetCourseSearch {
    private NetCourseSearchResult result;
    private String q;
    private String type;
    private String status;

    public NetCourseSearchResult getResult() {
        return this.result;
    }

    public void setResult(NetCourseSearchResult result) {
        this.result = result;
    }

    public String getQ() {
        return this.q;
    }

    public void setQ(String q) {
        this.q = q;
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
