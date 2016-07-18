package com.moonsister.im.login.presenter;

import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.login.view.LoginFragmentView;

/**
 * Created by jb on 2016/6/17.
 */
public interface LoginFragmentPersenter extends BaseIPresenter<LoginFragmentView> {
    void loginSubmit(String phone, String password);
}
