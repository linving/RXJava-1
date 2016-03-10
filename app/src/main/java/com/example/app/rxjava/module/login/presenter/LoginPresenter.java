package com.example.app.rxjava.module.login.presenter;

import com.example.app.rxjava.R;
import com.example.app.rxjava.bean.User;
import com.example.app.rxjava.module.login.model.LoginModel;
import com.example.app.rxjava.module.login.model.ia.LoginIA;
import com.example.app.rxjava.module.login.view.LoginActivity;
import com.example.app.rxjava.module.login.view.ia.LoginViewIA;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/2/29.
 */
public class LoginPresenter {
    private LoginViewIA mLoginViewIA;
    private LoginIA mLoginIA;

    public LoginPresenter(LoginViewIA mLoginViewIA) {
        this.mLoginViewIA = mLoginViewIA;
        this.mLoginIA = new LoginModel();
    }

    public void login() {
        final User user = mLoginViewIA.getInfo();
        mLoginIA.validUserInfo(user)
            .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                @Override
                public Observable<Boolean> call(Boolean isValid) {
                    if (isValid) {
                        return mLoginIA.login(user.getEmail(), user.getPassword());
                    }
                    return null;
                }
            })
            .subscribeOn(Schedulers.io())// 在非UI线程中执行getUser
            .doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    mLoginViewIA.showProgressDialog(); // 需要在主线程执行
                }
            })
            .subscribeOn(AndroidSchedulers.mainThread()) // 指定主线程
            .observeOn(AndroidSchedulers.mainThread())// 在UI线程中执行结果
            .subscribe(new Subscriber<Boolean>() {
                @Override
                public void onNext(Boolean isValid) {
                    if (isValid != null && isValid) {
                        mLoginViewIA.toNextView();
                    } else {
                        mLoginViewIA.showError(((LoginActivity) mLoginViewIA).getString(R.string.error_invalid_email));
                    }
                }

                @Override
                public void onError(Throwable e) {
                    mLoginViewIA.showError(e.getMessage());
                    mLoginViewIA.hideProgressDialog();
                }

                @Override
                public void onCompleted() {
                    mLoginViewIA.hideProgressDialog();
                }
            });
    }
}
