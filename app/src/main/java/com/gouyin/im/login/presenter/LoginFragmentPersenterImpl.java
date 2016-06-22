package com.gouyin.im.login.presenter;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.LoginBean;
import com.gouyin.im.login.model.LoginFragmentModel;
import com.gouyin.im.login.model.LoginFragmentModelImpl;
import com.gouyin.im.login.view.LoginFragmentView;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;

/**
 * Created by jb on 2016/6/17.
 */
public class LoginFragmentPersenterImpl implements LoginFragmentPersenter, BaseIModel.onLoadDateSingleListener<LoginBean> {
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
    public void loginSubmit(String phone, String password) {
        loginView.showLoading();
        loginFragmentModel.login(phone, password, this);

    }

    @Override
    public void onSuccess(LoginBean loginBean, BaseIModel.DataType dataType) {
        loginView.hideLoading();
        if (loginBean != null) {
            if ("1".equals(loginBean.getCode())) {
                UIUtils.sendDelayedOneMillis(new Runnable() {
                    @Override
                    public void run() {
                        loginView.loginSuccss();
                    }
                });

            }
            loginView.transfePageMsg(loginBean.getMsg());
        } else
            loginView.transfePageMsg(UIUtils.getResources().getString(R.string.str_login) + UIUtils.getStringRes(R.string.request_failed));


    }

    @Override
    public void onFailure(String msg, Throwable e) {
        loginView.transfePageMsg(UIUtils.getResources().getString(R.string.str_login) + UIUtils.getStringRes(R.string.request_failed));
        LogUtils.e(this, msg);
    }
}
