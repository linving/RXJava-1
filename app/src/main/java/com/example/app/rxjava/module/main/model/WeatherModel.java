package com.example.app.rxjava.module.main.model;

import com.example.app.rxjava.base.BaseModel;
import com.example.app.rxjava.base.ResultsDeserializer;
import com.example.app.rxjava.bean.weather.WeatherData;
import com.example.app.rxjava.bean.weather.WeatherListData;
import com.example.app.rxjava.module.main.model.ia.WeatherIA;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/2/29.
 */
public class WeatherModel extends BaseModel implements WeatherIA {
    private static final String ENDPOINT = "http://api.openweathermap.org";
    private final Service mService;

    public WeatherModel() {
        // 对返回的数据进行解析
        Gson gsonInstance = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<WeatherListData>() {
                        }.getType(),
                        new ResultsDeserializer<WeatherListData>())
                .create();

        // 适配器
        Retrofit mRetrofit = getRetrofitBuilder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gsonInstance))
                .build();

        // 服务
        mService = mRetrofit.create(Service.class);
    }
    public Observable<List<WeatherData>> getServerData(final String lat, final String lon, final String cnt) {
        return mService.getWeatherList(lat, lon, cnt, "82baf3673f8b23cb70d1165d3ce73b9c")
                .flatMap(new Func1<WeatherListData, Observable<List<WeatherData>>>() {
                    @Override
                    public Observable<List<WeatherData>> call(WeatherListData data) {
                        List<WeatherData> list = data.getList();
                        return Observable.just(list);
                    }
                });
    }

    /**
     * 服务接口
     */
    private interface Service {

        @GET("/data/2.5/find")
        Observable<WeatherListData> getWeatherList(@Query("lat") String lat, @Query("lon") String lon, @Query("cnt") String cnt, @Query("APPID") String appId);

    }
}
