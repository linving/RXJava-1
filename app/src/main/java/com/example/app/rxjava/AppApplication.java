package com.example.app.rxjava;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.example.app.rxjava.receiver.ConnectionReceiver;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Administrator on 2016/3/8.
 */
public class AppApplication extends Application {

    /*网络是否连接*/
    public static boolean isNetConnect;/*网络连接状态监听*/
    private ConnectionReceiver connectionReceiver = new ConnectionReceiver();
    public static AppApplication APP_APPLICATION;

    public static AppApplication getInstance() {
        return APP_APPLICATION;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Create global configuration and initialize ImageLoader with this config
        APP_APPLICATION = this;
        initImageLoader(getApplicationContext());
        registerNetReceiver();

        FlowManager.init(this);
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    /**
     * 注册网络连接状态监听
     */
    public void registerNetReceiver() {
        IntentFilter connectionIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectionReceiver, connectionIntentFilter);
    }

    /**
     * 注销网络连接状态监听
     */
    public void unregisterNetReceiver() {
        if (connectionReceiver != null) {
            unregisterReceiver(connectionReceiver);
        }
    }
}
