package com.gouyin.im.login.presenter;

import com.gouyin.im.R;
import com.gouyin.im.login.view.LoginMainView;


/**
 * Created by pc on 2016/6/13.
 */
public class LoginMainPresenterImpl implements LoginMainPresenter {
    private final LoginMainView view;

    public LoginMainPresenterImpl(LoginMainView view) {
        this.view = view;
    }

    @Override
    public void swicthNavigation(int id) {
        switch (id) {
            case R.id.tv_navigation_login:
                view.swicth2Login();
                break;
            case R.id.tv_navigation_register:
                view.swicth2Register();
                break;
        }
    }
}
