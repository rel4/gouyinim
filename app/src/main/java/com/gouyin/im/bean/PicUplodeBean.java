package com.gouyin.im.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jb on 2016/6/23.
 */
public class PicUplodeBean implements Serializable {
    public List<ImageObjoct> img;

    public List<ImageObjoct> getImg() {
        return img;
    }

    public void setImg(List<ImageObjoct> img) {
        this.img = img;
    }


}
