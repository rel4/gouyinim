package com.gouyin.im.main.model;

import com.gouyin.im.base.BaseIModel;

/**
 * Created by jb on 2016/7/7.
 */
public interface UserActionModel extends BaseIModel {
    /**
     * 关注
     *
     * @param uid
     * @param type
     */
    void wacthAction(String uid, String type, onLoadDateSingleListener listener);

//    void likeAction();

}
