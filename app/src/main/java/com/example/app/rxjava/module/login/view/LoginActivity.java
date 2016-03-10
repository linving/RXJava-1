package com.example.app.rxjava.module.login.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.database.Cursor;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app.rxjava.R;
import com.example.app.rxjava.base.BaseActivity;
import com.example.app.rxjava.bean.User;
import com.example.app.rxjava.module.login.presenter.LoginPresenter;
import com.example.app.rxjava.module.main.view.MainActivity;
import com.example.app.rxjava.module.user.view.UserActivity;
import com.example.app.rxjava.module.login.view.ia.LoginViewIA;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements LoginViewIA {
    private Context context;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login_form);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        presenter = new LoginPresenter(this);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.login();
            }
        });

        // TODO: 从Cookie中获取登录状态参数，判断是否直接跳转到内容页
    }

    @Override
    public User getInfo() {
        User user = new User();
        user.setEmail(mEmailView.getText().toString());
        user.setPassword(mPasswordView.getText().toString());
        return user;
    }

    @Override
    public void toNextView() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}

