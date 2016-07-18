package com.moonsister.im.my.persenter;

import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.bean.UserInfoChangeBean;
import com.moonsister.im.my.view.UserInfoChangeActivityView;

/**
 * Created by jb on 2016/7/11.
 */
public interface UserInfoChangeActivityPresenter extends BaseIPresenter<UserInfoChangeActivityView> {

    void loadbasicData();

    void submit(UserInfoChangeBean.DataBean dataBean);
}
