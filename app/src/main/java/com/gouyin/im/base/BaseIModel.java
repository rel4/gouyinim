package com.gouyin.im.base;

/**
 * Created by pc on 2016/6/14.
 */
public interface BaseIModel<T> {
    void loadPageData(onLoadDateListener<T> listener);
}
