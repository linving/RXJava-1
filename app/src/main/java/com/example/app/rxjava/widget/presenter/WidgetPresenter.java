package com.example.app.rxjava.widget.presenter;

import com.example.app.rxjava.AppApplication;
import com.example.app.rxjava.bean.picture.Data;
import com.example.app.rxjava.bean.picture.Picture;
import com.example.app.rxjava.widget.UILWidgetProvider;
import com.example.app.rxjava.widget.model.WidgetModel;
import com.example.app.rxjava.widget.model.ia.WidgetIA;

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
public class WidgetPresenter {
    private UILWidgetProvider widgetProvider;
    private WidgetIA mWidgetIA;
    private int totalNum;

    public WidgetPresenter(UILWidgetProvider widgetProvider) {
        this.widgetProvider = widgetProvider;
        this.mWidgetIA = new WidgetModel();
    }

    public void loadData() {
        if (!AppApplication.isNetConnect) {
            return;
        }
        mWidgetIA.getData()
            .flatMap(new Func1<Picture, Observable<List<String>>>() {
                @Override
                public Observable<List<String>> call(Picture picture) {
                    totalNum = picture.getTotalNum();
                    List<String> imgList = getImageList(picture);
                    return Observable.just(imgList);
                }
            })
            .subscribeOn(Schedulers.io())// 在非UI线程中执行getUser
            .doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    // 需要在主线程执行
                }
            })
            .subscribeOn(AndroidSchedulers.mainThread()) // 指定主线程
            .observeOn(AndroidSchedulers.mainThread())// 在UI线程中执行结果
            .subscribe(new Subscriber<List<String>>() {
                @Override
                public void onNext(List<String> data) {
                    widgetProvider.getData(data);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onCompleted() {

                }
            });
    }

    /**
     * 得到网页中图片的地址
     */
    private List<String> getImageList(Picture picture) {
        List<String> imgList = new ArrayList<String>();
        List<Data> list = picture.getData();
        for(int i = 0, len = list.size(); i < len; i++) {
            imgList.add(list.get(i).getThumbnail_url());
        }
        return imgList;
    }
}
