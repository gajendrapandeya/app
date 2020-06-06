package com.copy.lms.ResponceModel;

public class NetTestResponceResponse {
    private NetTestResponceResponseTest test;
    private NetTestResponceResponseTest_result test_result;
    private boolean is_test_given;

    public NetTestResponceResponseTest getTest() {
        return this.test;
    }

    public void setTest(NetTestResponceResponseTest test) {
        this.test = test;
    }

    public NetTestResponceResponseTest_result getTest_result() {
        return this.test_result;
    }

    public void setTest_result(NetTestResponceResponseTest_result test_result) {
        this.test_result = test_result;
    }

    public boolean getIs_test_given() {
        return this.is_test_given;
    }

    public void setIs_test_given(boolean is_test_given) {
        this.is_test_given = is_test_given;
    }
}
