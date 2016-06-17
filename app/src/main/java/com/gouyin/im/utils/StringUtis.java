package com.gouyin.im.utils;

/**
 * Created by jb on 2016/6/17.
 */
public class StringUtis {
    /**
     * 判断是字符串
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.isEmpty())
            return true;
        return false;

    }
}
