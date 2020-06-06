package com.copy.lms.net;


import com.copy.lms.BaseAppClass;

public class RetrofitClient {
    private final static String TAG = RetrofitClient.class.getSimpleName();

    private static FinalWrapper<RetrofitClient> helperWrapper;
    private static final Object LOCK = new Object();
    private final RestClient mRestOkClient;


    private RetrofitClient() {
        // Rest client without basic authorization
        if (!BaseAppClass.getPreferences().isTokenAvailable())
            mRestOkClient = ServiceGenerator.createServiceNoAuth(RestClient.class);
        else
            mRestOkClient = ServiceGenerator.createService(RestClient.class);
    }

    /**
     * @return
     */
    public static RetrofitClient getInstance() {
        FinalWrapper<RetrofitClient> wrapper = helperWrapper;
//        if (wrapper == null) {
            synchronized (LOCK) {
//                if (helperWrapper == null) {
                    helperWrapper = new FinalWrapper<>(new RetrofitClient());
//                }
                wrapper = helperWrapper;
            }
//        }
        return wrapper.value;
    }

    public RestClient getRestOkClient() {
        return mRestOkClient;
    }

}