package com.example.app.rxjava.widget.model;

import com.example.app.rxjava.base.RequestInterceptor;
import com.example.app.rxjava.base.ResultsDeserializer;
import com.example.app.rxjava.bean.picture.Picture;
import com.example.app.rxjava.widget.model.ia.WidgetIA;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/2/29.
 */
public class WidgetModel implements WidgetIA {
    private static final String ENDPOINT = "http://image.baidu.com";
    private final Service mService;

    public static final int ROWNUM = 30;

    public WidgetModel() {
        // Log信息
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        // 拦截器，在请求中增加额外参数
        RequestInterceptor requestInterceptor = new RequestInterceptor();

        // OkHttp3.0的使用方式
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();

        // 对返回的数据进行解析
        Gson gsonInstance = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<List<Picture>>() {
                        }.getType(),
                        new ResultsDeserializer<List<Picture>>())
                .create();

        // 适配器
        Retrofit marvelApiAdapter = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gsonInstance))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        // 服务
        mService = marvelApiAdapter.create(Service.class);
    }

    @Override
    public Observable<Picture> getData() {
        return mService.getData(0, ROWNUM, "宠物", "全部");
    }

    /**
     * 服务接口
     */
    private interface Service {

        @GET("/channel/listjson")
        Observable<Picture> getData(@Query("pn") int pn, @Query("rn") int rn, @Query("tag1") String tag1, @Query("tag2") String tag2);

    }
}
