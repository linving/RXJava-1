package com.example.app.rxjava.base;

import com.example.app.rxjava.AppApplication;
import com.example.app.rxjava.base.cookie.CookiesManager;
import com.example.app.rxjava.base.interceptor.CacheInterceptor;
import com.example.app.rxjava.base.interceptor.RequestInterceptor;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

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

            // 缓存拦截器
            CacheInterceptor cacheInterceptor = new CacheInterceptor();
            //设置缓存路径
            File httpCacheDirectory = new File(AppApplication.getInstance().getCacheDir(), "responses");
            //设置缓存大小 10M
            Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

            // OkHttp3.0的使用方式
            OkHttpClient client = new OkHttpClient.Builder()
                    .cookieJar(new CookiesManager())
                    .addInterceptor(requestInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(cacheInterceptor)
                    .addNetworkInterceptor(cacheInterceptor)
                    .cache(cache)
                    .build();

            // 适配器
            BUILDER = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client);
        }
        return BUILDER;
    }
}
