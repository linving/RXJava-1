package com.example.app.rxjava.module.main.model;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.example.app.rxjava.AppApplication;
import com.example.app.rxjava.R;
import com.example.app.rxjava.base.BaseModel;
import com.example.app.rxjava.base.ResultsDeserializer;
import com.example.app.rxjava.bean.weather.WeatherListData;
import com.example.app.rxjava.module.main.model.ia.ChartIA;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/2/29.
 */
public class ChartModel extends BaseModel implements ChartIA {
    private static final String ENDPOINT = "http://www.baidu.com";
    private final Service mService;

    public ChartModel() {
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

    @Override
    public Observable<LineData> getServerData(int count, float range) {
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 3;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        Drawable drawable = ContextCompat.getDrawable(AppApplication.getInstance(), R.drawable.fade_red);
        set1.setFillDrawable(drawable);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        return Observable.just(data);
    }

    /**
     * 服务接口
     */
    private interface Service {

        @GET("/s")
        Observable<WeatherListData> getData();

    }
}
