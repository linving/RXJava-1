package com.example.app.rxjava.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.app.rxjava.AppApplication;

/**
 * 检测网络连接状态
 * Created by Administrator on 2016/3/9.
 */
public class ConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = conn.getActiveNetworkInfo();
            if (info != null) {
                AppApplication.isNetConnect = info.isConnected();
            } else {
                AppApplication.isNetConnect = false;
            }
        }
    }
}
