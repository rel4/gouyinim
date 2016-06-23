package com.gouyin.im.base;

import java.io.Serializable;

/**
 * Created by jb on 2016/6/23.
 */
public class ImageObjoct implements Serializable{
    /**
     * 模糊图片
     */
    public String s;
    /**
     * 原图
     */
    public String l;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }
}

