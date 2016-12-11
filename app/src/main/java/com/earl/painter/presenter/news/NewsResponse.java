package com.earl.painter.presenter.news;

import java.io.Serializable;

/**
 * 作者：earl on 16/12/11 22:02
 * 邮箱：z604458675@gmail.com
 * 描述：Painter 响应解析基类
 */

public class NewsResponse<T> implements Serializable {
    private static final long serialVersionUID = -686453405647539973L;

    public String showapi_res_error;
    public int showapi_res_code;
    public T showapi_res_body;
}
