package com.example.app.rxjava.module.login.view.ia;

import com.example.app.rxjava.base.BaseViewIA;
import com.example.app.rxjava.bean.User;

/**
 * Created by Administrator on 2016/2/29.
 */
public interface LoginViewIA extends BaseViewIA {

    User getInfo();

    void toNextView();

}
