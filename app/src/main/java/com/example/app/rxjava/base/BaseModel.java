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
public class BaseModel {
    public static final String API_SERVER = "服务器地址";
    private static volatile HttpLoggingInterceptor loggingInterceptor;
    private static volatile RequestInterceptor requestInterceptor;
    private static volatile CacheInterceptor cacheInterceptor;
    private static volatile File httpCacheDirectory;
    private static volatile Cache cache;
    // OkHttp3.0的使用方式
    private static volatile OkHttpClient client;

    protected Retrofit.Builder getRetrofitBuilder() {
        if (loggingInterceptor == null) {
            synchronized (BaseModel.class) {
                // Log信息
                loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

                // 拦截器，在请求中增加额外参数
                requestInterceptor = new RequestInterceptor();

                // 缓存拦截器
                cacheInterceptor = new CacheInterceptor();
                //设置缓存路径
                httpCacheDirectory = new File(AppApplication.getInstance().getCacheDir(), "responses");
                //设置缓存大小 10M
                cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
                client = new OkHttpClient.Builder()
                        .cookieJar(new CookiesManager())
                        .addInterceptor(requestInterceptor)
                        .addInterceptor(loggingInterceptor)
                        .addInterceptor(cacheInterceptor)
                        .addNetworkInterceptor(cacheInterceptor)
                        .cache(cache)
                        .build();
            }
        }

        // 适配器
        Retrofit.Builder builder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client);
        return builder;
    }
}
