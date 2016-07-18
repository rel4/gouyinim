package com.moonsister.im.main.model;

import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.bean.UserInfoChangeBean;

/**
 * Created by jb on 2016/7/11.
 */
public interface UserinfoActivityModel extends BaseIModel {
    void loadBasicData(String uid, onLoadDateSingleListener<UserInfoChangeBean> listener);
}
