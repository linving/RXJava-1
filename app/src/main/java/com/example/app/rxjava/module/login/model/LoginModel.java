package com.example.app.rxjava.module.login.model;

import android.os.SystemClock;

import com.example.app.rxjava.bean.User;
import com.example.app.rxjava.module.login.model.ia.LoginIA;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/2/29.
 */
public class LoginModel implements LoginIA {
    public Observable<Boolean> validUserInfo(final User user) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                // 设置个2000ms的延迟，模拟网络访问、数据库操作等等延时操作
                SystemClock.sleep(2000);
                if(!user.getEmail().contains("@")) {
                    subscriber.onNext(false);
                } else if(user.getPassword().length() < 1) {
                    subscriber.onNext(false);
                } else {
                    subscriber.onNext(true);
                }
                subscriber.onCompleted();
            }
        });
    }
    public Observable<Boolean> login(final String email, final String password) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                // 设置个2000ms的延迟，模拟网络访问、数据库操作等等延时操作
                //SystemClock.sleep(2000);

                if(email.equals("q@qq.com") && password.equals("1")) {
                    subscriber.onNext(true);
                } else {
                    subscriber.onNext(false);
                }
                subscriber.onCompleted();
            }
        });
    }
}
