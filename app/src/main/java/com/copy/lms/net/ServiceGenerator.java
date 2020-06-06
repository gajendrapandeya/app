package com.copy.lms.net;

import com.copy.lms.BaseAppClass;
import com.copy.lms.Config;
import com.copy.lms.util.Constants;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class ServiceGenerator {
    // No need to instantiate this class.
    private ServiceGenerator() {
    }

    /**
     * @param serviceClass
     * @param <S>
     * @return
     */
    public static <S> S createServiceNoAuth(Class<S> serviceClass) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(Config.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(okHttpClient));

        builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                // create Base64 encodet string
                request.addHeader("Accept", "application/json");
            }
        });

        RestAdapter adapter = builder.build();


        return adapter.create(serviceClass);

    }

    /**
     * @param serviceClass
     * @param <S>
     * @return
     */
    public static <S> S createService(Class<S> serviceClass) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(Config.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(okHttpClient));

            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("Accept", "application/json");
                    request.addHeader("Authorization", "Bearer " + BaseAppClass.getPreferences().getToken());
                }
            });

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }
}
