package com.gouyin.im.main.model;

import com.gouyin.im.ServerApi;
import com.gouyin.im.aliyun.AliyunManager;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UserInfoUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/6/24.
 */
public class PlayUserAcitivityModelImpl implements PlayUserAcitivityModel {


    @Override
    public void aliPay(String playType, String uid, String money) {
        String authcode = UserInfoUtils.getAuthcode();
        ServerApi.getAppAPI().aliPay(playType, uid, money, authcode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DefaultDataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(PlayUserAcitivityModelImpl.this, " onError " + e.getMessage());
                    }

                    @Override
                    public void onNext(DefaultDataBean defaultDataBean) {
                        LogUtils.e(PlayUserAcitivityModelImpl.this, " onNext " +defaultDataBean.toString());
                        if (defaultDataBean != null && defaultDataBean.getObj() instanceof String) {
                            String res = (String) defaultDataBean.getObj();
                            LogUtils.e(PlayUserAcitivityModelImpl.this, " defaultDataBean " + res );
                            AliyunManager.getInstance().play(res);
                        }

                    }
                });
    }


}
