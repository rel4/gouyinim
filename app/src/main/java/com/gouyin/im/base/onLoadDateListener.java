package com.gouyin.im.base;

import java.util.List;

/**
 * Created by pc on 2016/6/3.
 */
public interface onLoadDateListener<T> {
    /**
     * 数据加载成功
     *
     * @param t
     * @param
     */
     void onSuccess(T t);

    /**
     * 数据加载失败
     *
     * @param msg
     * @param e
     */
    void onFailure(String msg, Exception e);
}
