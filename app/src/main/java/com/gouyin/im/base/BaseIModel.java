package com.gouyin.im.base;

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
        void onSuccess(T t, DataType dataType);

        /**
         * 数据加载失败
         *
         * @param msg
         */
        void onFailure(String msg);


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
         * @param
         * @param
         */
        void onSuccess(List<T> t, DataType dataType);

        /**
         * 数据加载失败
         *
         * @param msg
         */
        void onFailure(String msg);


    }

    public enum DataType {
        DATA_ZERO(0), DATA_ONE(1), DATA_TWO(2), DATA_THREE(3), DATA_FOUR(4);
        private int value;

        private DataType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
