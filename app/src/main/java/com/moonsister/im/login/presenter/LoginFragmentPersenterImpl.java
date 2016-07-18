package com.moonsister.im.login.presenter;

import com.moonsister.im.AppConstant;
import com.moonsister.im.R;
import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.bean.LoginBean;
import com.moonsister.im.bean.PersonInfoDetail;
import com.moonsister.im.login.model.LoginFragmentModel;
import com.moonsister.im.login.model.LoginFragmentModelImpl;
import com.moonsister.im.login.view.LoginFragmentView;
import com.moonsister.im.manager.UserInfoManager;
import com.moonsister.im.utils.LogUtils;
import com.moonsister.im.utils.StringUtis;
import com.moonsister.im.utils.UIUtils;

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
            if (StringUtis.equals(loginBean.getCode(), AppConstant.code_request_success)) {
                PersonInfoDetail data = loginBean.getData();
                data.setLogin(true);
                if (data != null)
                    UserInfoManager.getInstance().saveMemoryInstance(data);
                UIUtils.sendDelayedOneMillis(new Runnable() {
                    @Override
                    public void run() {
                        loginView.loginSuccss();
                    }
                });

            }
            loginView.transfePageMsg(loginBean.getMsg());
        } else {
            loginView.transfePageMsg(UIUtils.getResources().getString(R.string.str_login) + UIUtils.getStringRes(R.string.request_failed));

        }
        loginView.hideLoading();


    }

    @Override
    public void onFailure(String msg) {

        loginView.transfePageMsg(UIUtils.getResources().getString(R.string.str_login) + UIUtils.getStringRes(R.string.request_failed));
        LogUtils.e(this, msg);
        loginView.hideLoading();
    }
}
