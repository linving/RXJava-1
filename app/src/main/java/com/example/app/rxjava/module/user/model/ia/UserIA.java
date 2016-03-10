package com.example.app.rxjava.module.user.model.ia;

import com.example.app.rxjava.bean.User;

import rx.Observable;

/**
 * Created by Administrator on 2016/2/29.
 */
public interface UserIA {
    public Observable<User> getUser();
}
