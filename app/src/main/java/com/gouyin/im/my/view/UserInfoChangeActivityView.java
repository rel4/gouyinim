package com.gouyin.im.my.view;

import com.gouyin.im.base.BaseIView;
import com.gouyin.im.bean.UserInfoChangeBean;

/**
 * Created by jb on 2016/7/11.
 */
public interface UserInfoChangeActivityView extends BaseIView {
    void setUserBasic(UserInfoChangeBean userInfoChangeBean);

    void pageFinish();
}
