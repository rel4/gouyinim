package com.gouyin.im.login.model;

import com.gouyin.im.ServerApi;
import com.gouyin.im.bean.LoginBean;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.MD5Util;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/6/17.
 */
public class LoginFragmentModelImpl  implements  LoginFragmentModel{
    @Override
    public void login(String phone, String password,  onLoadDateListener<LoginBean> Listener) {
        ServerApi.getAppAPI().login(phone, MD5Util.string2MD5(password))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Listener.onFailure(e.getMessage(),e);
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        Listener.onSuccess(loginBean,0);
                    }
                });
    }
}
