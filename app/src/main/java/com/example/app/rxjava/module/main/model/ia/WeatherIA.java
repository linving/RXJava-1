package com.example.app.rxjava.module.main.model.ia;

import com.example.app.rxjava.bean.User;
import com.example.app.rxjava.bean.weather.WeatherData;
import com.example.app.rxjava.bean.weather.WeatherListData;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/2/29.
 */
public interface WeatherIA {
    public Observable<WeatherListData> getServerData(final String lat, final String lon, final String cnt);
    public Observable<List<WeatherData>> getLocalData();
}
