package com.earl.painter.presenter.news;

import com.earl.painter.utils.Convert;
import com.lzy.okgo.convert.Converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * 作者：earl on 16/12/11 21:59
 * 邮箱：z604458675@gmail.com
 * 描述：Painter 新闻解析层
 */

public class NewsConvert<T> implements Converter<T> {

    private ParameterizedType type;

    public static <T> NewsConvert<T> create() {
        return new NewsConvert<>();
    }

    public void setType(ParameterizedType type) {
        this.type = type;
    }

    @Override
    public T convertSuccess(Response response) throws Exception {
        com.google.gson.stream.JsonReader jsonReader = new com.google.gson.stream.JsonReader(response.body().charStream());
        Type rawType = type.getRawType();
        if (rawType == NewsResponse.class) {
            NewsResponse newsResponse = Convert.fromJson(jsonReader, type);
            if (newsResponse.showapi_res_code == 0) {
                //noinspection unchecked
                return (T) newsResponse;
            } else {
                throw new IllegalStateException(newsResponse.showapi_res_error);
            }
        }
        throw new IllegalStateException("基类错误无法解析!");
    }
}