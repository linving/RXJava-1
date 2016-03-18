package com.example.app.rxjava.module.main.model.ia;

import com.example.app.rxjava.bean.weather.WeatherData;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/2/29.
 */
public interface WeatherIA {
    public Observable<List<WeatherData>> getServerData(final String lat, final String lon, final String cnt);
}
