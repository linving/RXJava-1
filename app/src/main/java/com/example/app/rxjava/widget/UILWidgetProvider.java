package com.example.app.rxjava.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RemoteViews;

import com.example.app.rxjava.AppApplication;
import com.example.app.rxjava.R;
import com.example.app.rxjava.widget.presenter.WidgetPresenter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Example widget provider
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class UILWidgetProvider extends AppWidgetProvider {

    private static DisplayImageOptions displayOptions;
    private WidgetPresenter presenter;
    private UpdateWidgetListener listener;

    static {
        displayOptions = DisplayImageOptions.createSimple();
    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        presenter = new WidgetPresenter(this);
        presenter.loadData();

        AppApplication.initImageLoader(context);

        listener = new UpdateWidgetListener() {
            @Override
            public void refresh(List<String> data) {
                final int widgetCount = appWidgetIds.length;
                for (int i = 0; i < widgetCount; i++) {
                    int appWidgetId = appWidgetIds[i];
                    updateAppWidget(context, appWidgetManager, appWidgetId, data);
                }
            }
        };
    }

    static void updateAppWidget(Context context, final AppWidgetManager appWidgetManager, final int appWidgetId, final List<String> data) {
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        ImageSize minImageSize = new ImageSize(70, 70); // 70 - approximate size of ImageView in widget
        ImageLoader.getInstance()
                .loadImage(data.get(0), minImageSize, displayOptions, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        views.setImageViewBitmap(R.id.image_left, loadedImage);
                        appWidgetManager.updateAppWidget(appWidgetId, views);
                    }
                });
        ImageLoader.getInstance()
                .loadImage(data.get(1), minImageSize, displayOptions, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        views.setImageViewBitmap(R.id.image_right, loadedImage);
                        appWidgetManager.updateAppWidget(appWidgetId, views);
                    }
                });
    }

    public void getData(List<String> data) {
        listener.refresh(data);
    }

    interface UpdateWidgetListener {
        public void refresh(List<String> data);
    }
}
