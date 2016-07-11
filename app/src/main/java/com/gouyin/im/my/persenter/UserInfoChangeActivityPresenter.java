package com.gouyin.im.my.persenter;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.bean.UserInfoChangeBean;
import com.gouyin.im.my.view.UserInfoChangeActivityView;

/**
 * Created by jb on 2016/7/11.
 */
public interface UserInfoChangeActivityPresenter extends BaseIPresenter<UserInfoChangeActivityView> {

    void loadbasicData();

    void submit(UserInfoChangeBean.DataBean dataBean);
}
