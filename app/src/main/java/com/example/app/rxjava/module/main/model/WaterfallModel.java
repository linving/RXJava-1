package com.example.app.rxjava.module.main.model;

import com.example.app.rxjava.base.BaseModel;
import com.example.app.rxjava.base.RequestInterceptor;
import com.example.app.rxjava.base.ResultsDeserializer;
import com.example.app.rxjava.bean.picture.Data;
import com.example.app.rxjava.bean.picture.Picture;
import com.example.app.rxjava.module.main.model.ia.WaterfallIA;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.sql.language.Select;

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
public class WaterfallModel extends BaseModel implements WaterfallIA {
    private static final String ENDPOINT = "http://image.baidu.com";
    private final Service mService;

    public static final int ROWNUM = 30;

    public WaterfallModel() {
        // 对返回的数据进行解析
        Gson gsonInstance = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<List<Picture>>() {
                        }.getType(),
                        new ResultsDeserializer<List<Picture>>())
                .create();

        // 适配器
        Retrofit mRetrofit = getRetrofitBuilder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gsonInstance))
                .build();

        // 服务
        mService = mRetrofit.create(Service.class);
    }

    @Override
    public Observable<Picture> getServerData(int pageNum) {
        return mService.getData(pageNum, ROWNUM, "宠物", "全部");
    }

    @Override
    public Observable<List<Data>> getLocalData() {
        List<Data> data = new Select().from(Data.class).queryList();
        return Observable.just(data);
    }

    /**
     * 服务接口
     */
    private interface Service {

        @GET("/channel/listjson")
        Observable<Picture> getData(@Query("pn") int pn, @Query("rn") int rn, @Query("tag1") String tag1, @Query("tag2") String tag2);

    }
}
