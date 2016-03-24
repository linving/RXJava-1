package com.example.app.rxjava.base.converter.html;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by Administrator on 2016/3/24.
 */
public class HtmlRequestBodyConverter<T>  implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/html; charset=UTF-8");

    HtmlRequestBodyConverter() {

    }

    @Override
    public RequestBody convert(T value) throws IOException {
        Log.e("data", value.toString());
        return RequestBody.create(MEDIA_TYPE, value.toString());
    }
}
