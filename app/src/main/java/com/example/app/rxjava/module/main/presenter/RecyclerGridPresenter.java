package com.example.app.rxjava.module.main.presenter;

import com.example.app.rxjava.bean.weather.WeatherData;
import com.example.app.rxjava.module.main.model.WeatherModel;
import com.example.app.rxjava.module.main.model.ia.WeatherIA;
import com.example.app.rxjava.module.main.view.ia.RecyclerGridViewIA;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/2/29.
 */
public class RecyclerGridPresenter {
    private RecyclerGridViewIA mRecyclerGridViewIA;
    private WeatherIA mWeatherIA;
    private static final String LAT = "55.5", LON = "37.5", CNT = "10";

    public RecyclerGridPresenter(RecyclerGridViewIA mRecyclerGridViewIA) {
        this.mRecyclerGridViewIA = mRecyclerGridViewIA;
        this.mWeatherIA = new WeatherModel();
    }

    public void loadData(final int pageNum) {
        mWeatherIA.getServerData(LAT, LON, CNT)
                .subscribeOn(Schedulers.io())// 在非UI线程中执行getUser
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRecyclerGridViewIA.showProgressDialog(); // 需要在主线程执行
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread()) // 指定主线程
                .observeOn(AndroidSchedulers.mainThread())// 在UI线程中执行结果
                .subscribe(new Subscriber<List<WeatherData>>() {
                    @Override
                    public void onNext(List<WeatherData> data) {
                        if (pageNum == 0) {
                            mRecyclerGridViewIA.refresh(data);
                        } else {
                            mRecyclerGridViewIA.loadNews(data);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRecyclerGridViewIA.showError(e.getMessage());
                        mRecyclerGridViewIA.hideProgressDialog();
                    }

                    @Override
                    public void onCompleted() {
                        mRecyclerGridViewIA.hideProgressDialog();
                    }
                });
    }
}
