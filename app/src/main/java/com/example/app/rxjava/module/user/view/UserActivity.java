package com.example.app.rxjava.module.user.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.rxjava.R;
import com.example.app.rxjava.base.BaseActivity;
import com.example.app.rxjava.bean.User;
import com.example.app.rxjava.module.login.view.LoginActivity;
import com.example.app.rxjava.module.main.view.MainActivity;
import com.example.app.rxjava.module.user.presenter.UserPresenter;
import com.example.app.rxjava.module.user.view.ia.UserViewIA;

/**
 * Created by Administrator on 2016/2/29.
 */
public class UserActivity extends BaseActivity implements UserViewIA {
    private Context context;

    private TextView mTvShow;

    private UserPresenter mUserPresenter;

    //点击返回按钮计数器，连续点击两次则退出应用
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        context = this;

        mUserPresenter = new UserPresenter(this);

        mTvShow = (TextView) findViewById(R.id.tv_show);
        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserPresenter.getUser();
            }
        });
    }

    @Override
    public void updateView(User user) {
        if (user == null) return;
        mTvShow.setText(user.getName());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
            return true;
        }else if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
