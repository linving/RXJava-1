package com.example.app.rxjava.base.converter.html;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Administrator on 2016/3/24.
 */
public class HtmlResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    HtmlResponseBodyConverter() {

    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        return (T) value.string();
    }
}