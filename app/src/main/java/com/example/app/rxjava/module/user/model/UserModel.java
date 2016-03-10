package com.example.app.rxjava.module.user.model;

import android.os.SystemClock;

import com.example.app.rxjava.bean.User;
import com.example.app.rxjava.module.user.model.ia.UserIA;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/2/29.
 */
public class UserModel implements UserIA {
    public Observable<User> getUser() {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                // 设置个2000ms的延迟，模拟网络访问、数据库操作等等延时操作
                SystemClock.sleep(2000);

//                final User user = null;
                final User user = new User();
                user.setName("Smith");
                if (user == null) {
                    subscriber.onError(new Exception("User = null"));
                } else {
                    subscriber.onNext(user);
                    subscriber.onCompleted();
                }
            }
        });
    }
}
