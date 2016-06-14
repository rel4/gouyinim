package com.gouyin.im.base;

/**
 * Created by pc on 2016/6/14.
 */
public interface BaseIPresenter<T> {
    void onCreate();

    void attachView(T t);
}
