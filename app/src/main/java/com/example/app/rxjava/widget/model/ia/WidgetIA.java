package com.example.app.rxjava.widget.model.ia;

import com.example.app.rxjava.bean.picture.Picture;

import rx.Observable;

/**
 * Created by Administrator on 2016/2/29.
 */
public interface WidgetIA {
    public Observable<Picture> getData();
}
