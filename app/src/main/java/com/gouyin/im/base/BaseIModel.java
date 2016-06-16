package com.gouyin.im.base;

/**
 * Created by pc on 2016/6/14.
 */
public interface BaseIModel {

    public interface onLoadDateListener<T> {
        /**
         * 数据加载成功
         *
         * @param t
         * @param
         */
        void onSuccess(T t,int dataType);

        /**
         * 数据加载失败
         *
         * @param msg
         * @param e
         */
        void onFailure(String msg, Throwable e);
    }
}
