package com.gouyin.im.login.model;

import com.gouyin.im.ServerApi;
import com.gouyin.im.bean.BaseBean;


import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/6/15.
 */
public class RegiterDataFragmentModelImpl implements RegiterDataFragmentModel {



    @Override
    public void login(String face, String sex, String pwd, String authcode, onLoadDateListener<BaseBean> listener) {
        ServerApi.getAppAPI().regiterLogin(face,sex,pwd,authcode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                            listener.onFailure(e.getMessage(),e);
                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                            listener.onSuccess(baseBean);
                    }
                });
    }
}
