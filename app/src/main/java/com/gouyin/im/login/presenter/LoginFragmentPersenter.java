package com.gouyin.im.login.presenter;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.login.view.LoginFragmentView;

/**
 * Created by jb on 2016/6/17.
 */
public interface LoginFragmentPersenter extends BaseIPresenter<LoginFragmentView> {
    void loginSubmit(String phone, String password);
}
