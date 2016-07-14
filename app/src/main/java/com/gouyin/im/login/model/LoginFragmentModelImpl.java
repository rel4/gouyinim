package com.gouyin.im.login.model;

import com.gouyin.im.ServerApi;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.LoginBean;
import com.gouyin.im.utils.MD5Util;
import com.gouyin.im.utils.ObservableUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/6/17.
 */
public class LoginFragmentModelImpl implements LoginFragmentModel {
    @Override
    public void login(String phone, String password, onLoadDateSingleListener<LoginBean> Listener) {
        Observable<LoginBean> observable = ServerApi.getAppAPI().login(phone, MD5Util.string2MD5(password));
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
