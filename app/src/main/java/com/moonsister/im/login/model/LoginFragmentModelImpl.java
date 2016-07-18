package com.moonsister.im.login.model;

import com.moonsister.im.AppConstant;
import com.moonsister.im.ServerApi;
import com.moonsister.im.bean.LoginBean;
import com.moonsister.im.utils.MD5Util;
import com.moonsister.im.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/6/17.
 */
public class LoginFragmentModelImpl implements LoginFragmentModel {
    @Override
    public void login(String phone, String password, onLoadDateSingleListener<LoginBean> Listener) {
        Observable<LoginBean> observable = ServerApi.getAppAPI().login(phone, MD5Util.string2MD5(password), AppConstant.CHANNEL_ID);
        ObservableUtils.parser(observable, new ObservableUtils.Callback<LoginBean>() {
            @Override
            public void onSuccess(LoginBean bean) {
                Listener.onSuccess(bean, DataType.DATA_ZERO);
            }

            @Override
            public void onFailure(String msg) {

                Listener.onFailure(msg);
            }
        });
    }
}
