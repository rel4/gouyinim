package com.gouyin.im.my.model;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.UserInfoChangeBean;

/**
 * Created by jb on 2016/7/11.
 */
public interface UserInfoChangeActivityModel extends BaseIModel {
    void loadBasiData(onLoadDateSingleListener listener);

    void submit(UserInfoChangeBean.DataBean dataBean, onLoadDateSingleListener listener);
}
