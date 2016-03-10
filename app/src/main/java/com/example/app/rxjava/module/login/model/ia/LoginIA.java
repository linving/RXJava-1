package com.example.app.rxjava.module.login.model.ia;

import com.example.app.rxjava.bean.User;

import rx.Observable;

/**
 * Created by Administrator on 2016/2/29.
 */
public interface LoginIA {
    public Observable<Boolean> validUserInfo(final User user);
    public Observable<Boolean> login(final String email, final String password);
}
