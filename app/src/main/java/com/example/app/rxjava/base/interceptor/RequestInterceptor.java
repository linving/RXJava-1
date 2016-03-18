package com.example.app.rxjava.base.interceptor;

/**
 * Created by Administrator on 2016/3/4.
 */

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加请求中公用的参数
 * <p>
 *
 */
public class RequestInterceptor implements Interceptor {

    private final String mApiKey = "111111";

    public RequestInterceptor() {

    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request oldRequest = chain.request();
        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter("mApiKey", mApiKey);

        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "*/*")
                .addHeader("Cookie", "add cookies here")
                .build();

        return chain.proceed(newRequest);
    }
}
