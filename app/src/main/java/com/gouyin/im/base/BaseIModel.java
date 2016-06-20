package com.gouyin.im.base;

import com.gouyin.im.bean.GoodSelectBaen;

import java.util.List;

/**
 * Created by pc on 2016/6/14.
 */
public interface BaseIModel {

    public interface onLoadDateSingleListener<T> {
        /**
         * 数据加载成功
         *
         * @param t
         * @param
         */
        void onSuccess(T t, int dataType);

        /**
         * 数据加载失败
         *
         * @param msg
         * @param e
         */
        void onFailure(String msg, Throwable e);


    }

    /**
     * 列表数据
     *
     * @param <T>
     */
    public interface onLoadListDateListener<T> {
        /**
         * 数据加载成功
         *
         * @param t
         * @param
         */
        void onSuccess(List<T> t, int dataType);

        /**
         * 数据加载失败
         *
         * @param msg
         * @param e
         */
        void onFailure(String msg, Throwable e);


    }
}
