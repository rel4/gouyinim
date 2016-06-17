package com.gouyin.im.login.presenter;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.LoginBean;
import com.gouyin.im.login.model.LoginFragmentModel;
import com.gouyin.im.login.model.LoginFragmentModelImpl;
import com.gouyin.im.login.view.LoginFragmentView;

/**
 * Created by jb on 2016/6/17.
 */
public class LoginFragmentPersenterImpl implements LoginFragmentPersenter, BaseIModel.onLoadDateListener<LoginBean> {
    private LoginFragmentView loginView;
    private LoginFragmentModel loginFragmentModel;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(LoginFragmentView loginFragmentView) {
        this.loginView = loginFragmentView;
        loginFragmentModel = new LoginFragmentModelImpl();
    }


    @Override
    public void login(String phone, String password) {
        loginView.showLoading();
        loginFragmentModel.login(phone,password,this);

    }

    @Override
    public void onSuccess(LoginBean loginBean, int dataType) {
        loginView.loginSuccss();
        loginView.hideLoading();

    }

    @Override
    public void onFailure(String msg, Throwable e) {

    }
}
