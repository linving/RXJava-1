package com.example.app.rxjava.base;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/3/4.
 */
public interface BaseViewIA {

    void showProgressDialog();

    void hideProgressDialog();

    void showError(String msg);
}
