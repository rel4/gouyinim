package com.moonsister.im.my.view;

import com.moonsister.im.base.BaseIView;
import com.moonsister.im.bean.UserInfoChangeBean;

/**
 * Created by jb on 2016/7/11.
 */
public interface UserInfoChangeActivityView extends BaseIView {
    void setUserBasic(UserInfoChangeBean userInfoChangeBean);

    void pageFinish();
}
