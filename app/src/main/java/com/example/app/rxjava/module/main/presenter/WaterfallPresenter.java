package com.example.app.rxjava.module.main.presenter;

import android.database.DatabaseUtils;

import com.example.app.rxjava.AppApplication;
import com.example.app.rxjava.bean.picture.Data;
import com.example.app.rxjava.bean.picture.Picture;
import com.example.app.rxjava.bean.weather.WeatherData;
import com.example.app.rxjava.module.main.model.WaterfallModel;
import com.example.app.rxjava.module.main.model.ia.WaterfallIA;
import com.example.app.rxjava.module.main.view.ia.WaterfallViewIA;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseHelperDelegate;

import java.util.ArrayList;
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
public class WaterfallPresenter {
    private WaterfallViewIA mWaterfallViewIA;
    private WaterfallIA mWaterfallIA;
    private int totalNum;

    public WaterfallPresenter(WaterfallViewIA mWaterfallViewIA) {
        this.mWaterfallViewIA = mWaterfallViewIA;
        this.mWaterfallIA = new WaterfallModel();
    }

    public void loadData(final int pageNum) {
        if(pageNum > 0 && pageNum * WaterfallModel.ROWNUM >= totalNum) {
            return;
        }
        if (!AppApplication.isNetConnect) {
            mWaterfallIA.getLocalData()
                    .flatMap(new Func1<List<Data>, Observable<List<String>>>() {
                        @Override
                        public Observable<List<String>> call(List<Data> data) {
                            List<String> imgList = new ArrayList<String>();
                            for(int i = 0, len = data.size(); i < len; i++) {
                                imgList.add(data.get(i).getThumbnail_url());
                            }
                            return Observable.just(imgList);
                        }
                    })
                    .subscribeOn(Schedulers.io())// 在非UI线程中执行getUser
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            mWaterfallViewIA.showProgressDialog(); // 需要在主线程执行
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread()) // 指定主线程
                    .observeOn(AndroidSchedulers.mainThread())// 在UI线程中执行结果
                    .subscribe(new Subscriber<List<String>>() {
                        @Override
                        public void onNext(List<String> data) {
                            if(pageNum == 0) {
                                mWaterfallViewIA.refresh(data);
                            } else {
                                mWaterfallViewIA.loadNews(data);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            mWaterfallViewIA.showError(e.getMessage());
                            mWaterfallViewIA.hideProgressDialog();
                        }

                        @Override
                        public void onCompleted() {
                            mWaterfallViewIA.hideProgressDialog();
                        }
                    });
        } else {
            mWaterfallIA.getServerData(pageNum)
                    .flatMap(new Func1<Picture, Observable<List<String>>>() {
                        @Override
                        public Observable<List<String>> call(Picture picture) {
                            TransactionManager.getInstance().saveOnSaveQueue(picture.getData());
                            totalNum = picture.getTotalNum();
                            List<String> list = getImageList(picture);
                            return Observable.just(list);
                        }
                    })
                    .subscribeOn(Schedulers.io())// 在非UI线程中执行getUser
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            mWaterfallViewIA.showProgressDialog(); // 需要在主线程执行
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread()) // 指定主线程
                    .observeOn(AndroidSchedulers.mainThread())// 在UI线程中执行结果
                    .subscribe(new Subscriber<List<String>>() {
                        @Override
                        public void onNext(List<String> data) {
                            if(pageNum == 0) {
                                mWaterfallViewIA.refresh(data);
                            } else {
                                mWaterfallViewIA.loadNews(data);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            mWaterfallViewIA.showError(e.getMessage());
                            mWaterfallViewIA.hideProgressDialog();
                        }

                        @Override
                        public void onCompleted() {
                            mWaterfallViewIA.hideProgressDialog();
                        }
                    });
        }
    }

    /**
     * 得到网页中图片的地址
     */
    public List<String> getImageList(Picture picture) {
        List<String> imgList = new ArrayList<String>();
        List<Data> list = picture.getData();
        for(int i = 0, len = list.size(); i < len; i++) {
            imgList.add(list.get(i).getThumbnail_url());
        }
        return imgList;
    }
}
