package com.example.app.rxjava.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app.rxjava.R;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/3/4.
 */
public class BaseActivity extends AppCompatActivity implements BaseViewIA {
    /**
     * 进度条
     */
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在加载，请稍后..");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
    }
    @Override
    public void hideProgressDialog() {
        mProgressDialog.hide();
    }
    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
