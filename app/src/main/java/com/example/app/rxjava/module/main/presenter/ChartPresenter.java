package com.example.app.rxjava.module.main.presenter;

import com.example.app.rxjava.module.main.model.ChartModel;
import com.example.app.rxjava.module.main.model.ia.ChartIA;
import com.example.app.rxjava.module.main.view.ia.ChartViewIA;
import com.github.mikephil.charting.data.LineData;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/2/29.
 */
public class ChartPresenter {
    private ChartViewIA mChartViewIA;
    private ChartIA mChartIA;

    public ChartPresenter(ChartViewIA mChartViewIA) {
        this.mChartViewIA = mChartViewIA;
        this.mChartIA = new ChartModel();
    }

    public void loadData(int count, float range) {
        mChartIA.getServerData(count, range)
                .subscribeOn(Schedulers.io())// 在非UI线程中执行getUser
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mChartViewIA.showProgressDialog(); // 需要在主线程执行
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread()) // 指定主线程
                .observeOn(AndroidSchedulers.mainThread())// 在UI线程中执行结果
                .subscribe(new Subscriber<LineData>() {
                    @Override
                    public void onNext(LineData data) {
                        mChartViewIA.refresh(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mChartViewIA.showError(e.getMessage());
                        mChartViewIA.hideProgressDialog();
                    }

                    @Override
                    public void onCompleted() {
                        mChartViewIA.hideProgressDialog();
                    }
                });
    }
}
