package com.example.app.rxjava.module.user.presenter;

import com.example.app.rxjava.bean.User;
import com.example.app.rxjava.module.user.model.UserModel;
import com.example.app.rxjava.module.user.model.ia.UserIA;
import com.example.app.rxjava.module.user.view.ia.UserViewIA;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/2/29.
 */
public class UserPresenter {
    private UserViewIA mUserViewIA;
    private UserIA mUserIA;

    public UserPresenter(UserViewIA mUserViewIA) {
        this.mUserViewIA = mUserViewIA;
        this.mUserIA = new UserModel();
    }

    public void getUser() {
        // 这里如果使用 Lambda 会更简洁
        mUserIA.getUser()
            .subscribeOn(Schedulers.io())// 在非UI线程中执行getUser
            .doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    mUserViewIA.showProgressDialog();
                }
            })
            .subscribeOn(AndroidSchedulers.mainThread()) // 指定主线程
            .observeOn(AndroidSchedulers.mainThread())// 在UI线程中执行结果
            .subscribe(new Subscriber<User>() {
                @Override
                public void onNext(User user) {
                    mUserViewIA.updateView(user);
                }
                @Override
                public void onError(Throwable e) {
                    mUserViewIA.showError(e.getMessage());
                    mUserViewIA.hideProgressDialog();
                }
                @Override
                public void onCompleted() {
                    mUserViewIA.hideProgressDialog();
                }
            });
    }
}
