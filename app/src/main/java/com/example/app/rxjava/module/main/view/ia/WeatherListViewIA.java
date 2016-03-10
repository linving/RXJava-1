package com.example.app.rxjava.module.main.view.ia;

import com.example.app.rxjava.base.BaseViewIA;
import com.example.app.rxjava.bean.weather.WeatherData;
import com.example.app.rxjava.bean.weather.WeatherListData;

import java.util.List;

/**
 * Created by Administrator on 2016/2/29.
 */
public interface WeatherListViewIA extends BaseViewIA {
    void refresh(List<WeatherData> data);
    void loadNews(List<WeatherData> data);
}
