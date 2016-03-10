package com.example.app.rxjava.module.main.view.ia;

import android.graphics.Bitmap;

import com.example.app.rxjava.base.BaseViewIA;

import java.util.List;

/**
 * Created by Administrator on 2016/2/29.
 */
public interface WaterfallViewIA extends BaseViewIA {
    void refresh(List<String> data);
    void loadNews(List<String> data);
}
