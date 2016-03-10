package com.example.app.rxjava.module.main.model.ia;

import com.example.app.rxjava.bean.picture.Data;
import com.example.app.rxjava.bean.picture.Picture;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016/2/29.
 */
public interface WaterfallIA {
    public Observable<Picture> getServerData(int pageNum);
    public Observable<List<Data>> getLocalData();
}
