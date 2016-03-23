package com.example.app.rxjava.module.main.model.ia;

import com.example.app.rxjava.bean.weather.WeatherData;
import com.github.mikephil.charting.data.LineData;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/2/29.
 */
public interface ChartIA {
    public Observable<LineData> getServerData(int count, float range);
}
