package com.example.app.rxjava.module.main.view.ia;

import com.example.app.rxjava.base.BaseViewIA;
import com.example.app.rxjava.bean.weather.WeatherData;
import com.github.mikephil.charting.data.LineData;

import java.util.List;

/**
 * Created by Administrator on 2016/2/29.
 */
public interface ChartViewIA extends BaseViewIA {
    void refresh(LineData data);
}
