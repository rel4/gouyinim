package com.gouyin.im.main.model;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.UserInfoChangeBean;

/**
 * Created by jb on 2016/7/11.
 */
public interface UserinfoActivityModel extends BaseIModel {
    void loadBasicData(String uid, onLoadDateSingleListener<UserInfoChangeBean> listener);
}
