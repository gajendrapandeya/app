package com.copy.lms.net;

/**
 * Created by rahul.patel on 7/15/2016.
 */
public interface OnApiCalled {

    void onSuccess(String response);

    void onError(String strError);
}
