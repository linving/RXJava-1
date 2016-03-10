package com.example.app.rxjava.base;

import android.content.Context;

import com.example.app.rxjava.AppApplication;
import com.example.app.rxjava.base.cookie.CookiesManager;
import com.example.app.rxjava.bean.picture.Picture;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/3/10.
 */
public abstract class BaseModel {
    public static final String API_SERVER = "服务器地址";
    private static Retrofit.Builder BUILDER;

    protected static Retrofit.Builder getRetrofitBuilder() {
        if (BUILDER == null) {
            // Log信息
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

            // 拦截器，在请求中增加额外参数
            RequestInterceptor requestInterceptor = new RequestInterceptor();

            // OkHttp3.0的使用方式
            OkHttpClient client = new OkHttpClient.Builder()
                    .cookieJar(new CookiesManager())
                    .addInterceptor(requestInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build();

            // 适配器
            BUILDER = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client);
        }
        return BUILDER;
    }
}
