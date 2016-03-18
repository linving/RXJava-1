package com.example.app.rxjava.module.main.presenter;

import com.example.app.rxjava.AppApplication;
import com.example.app.rxjava.bean.weather.WeatherData;
import com.example.app.rxjava.bean.weather.WeatherListData;
import com.example.app.rxjava.module.main.model.WeatherModel;
import com.example.app.rxjava.module.main.model.ia.WeatherIA;
import com.example.app.rxjava.module.main.view.ia.WeatherListViewIA;
import com.raizlabs.android.dbflow.runtime.TransactionManager;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/2/29.
 */
public class WeatherListPresenter {
    private WeatherListViewIA mWeatherListViewIA;
    private WeatherIA mWeatherIA;
    private static final String LAT = "55.5", LON = "37.5", CNT = "10";

    public WeatherListPresenter(WeatherListViewIA mWeatherListViewIA) {
        this.mWeatherListViewIA = mWeatherListViewIA;
        this.mWeatherIA = new WeatherModel();
    }

    public void loadData(final int pageNum) {
        mWeatherIA.getServerData(LAT, LON, CNT)
                .flatMap(new Func1<WeatherListData, Observable<List<WeatherData>>>() {
                    @Override
                    public Observable<List<WeatherData>> call(WeatherListData data) {
                        List<WeatherData> list = data.getList();
                        return Observable.just(list);
                    }
                })
                .subscribeOn(Schedulers.io())// 在非UI线程中执行getUser
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mWeatherListViewIA.showProgressDialog(); // 需要在主线程执行
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread()) // 指定主线程
                .observeOn(AndroidSchedulers.mainThread())// 在UI线程中执行结果
                .subscribe(new Subscriber<List<WeatherData>>() {
                    @Override
                    public void onNext(List<WeatherData> data) {
                        if (pageNum == 0) {
                            mWeatherListViewIA.refresh(data);
                        } else {
                            mWeatherListViewIA.loadNews(data);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mWeatherListViewIA.showError(e.getMessage());
                        mWeatherListViewIA.hideProgressDialog();
                    }

                    @Override
                    public void onCompleted() {
                        mWeatherListViewIA.hideProgressDialog();
                    }
                });
    }
}
