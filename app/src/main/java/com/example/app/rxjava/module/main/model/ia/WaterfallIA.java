package com.example.app.rxjava.module.main.model.ia;

import com.example.app.rxjava.bean.picture.Picture;

import rx.Observable;

/**
 * Created by Administrator on 2016/2/29.
 */
public interface WaterfallIA {
    public Observable<Picture> getServerData(int pageNum);
}
