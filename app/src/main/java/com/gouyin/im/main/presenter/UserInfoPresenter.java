package com.gouyin.im.main.presenter;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.main.view.UserInfoView;

/**
 * Created by pc on 2016/6/6.
 */
public interface UserInfoPresenter  extends BaseIPresenter<UserInfoView>{
    void loadonRefreshData(String userId);

    void loadLoadMoreData(String userId);

    void switchNavigation(int id);

    void loadUserInfodetail(String uid);
}
