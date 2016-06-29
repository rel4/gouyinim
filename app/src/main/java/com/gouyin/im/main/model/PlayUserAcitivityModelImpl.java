package com.gouyin.im.main.model;

import com.gouyin.im.R;
import com.gouyin.im.ServerApi;
import com.gouyin.im.aliyun.AliyunManager;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.utils.UserInfoUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/6/24.
 */
public class PlayUserAcitivityModelImpl implements PlayUserAcitivityModel {


    @Override
    public void aliPay(PayType playType, String uid, String money, onLoadDateSingleListener listener) {
        String authcode = UserInfoUtils.getAuthcode();
        ServerApi.getAppAPI().aliPay(playType.getType(), uid, money, authcode)
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
                        LogUtils.e(PlayUserAcitivityModelImpl.this, " onNext " + defaultDataBean.toString());
                        if (defaultDataBean != null && defaultDataBean.getObj() instanceof String) {
                            String res = (String) defaultDataBean.getObj();
                            LogUtils.e(PlayUserAcitivityModelImpl.this, " defaultDataBean " + res);
                            startAliPlayApp(res, listener);
                        } else
                            listener.onFailure(UIUtils.getStringRes(R.string.request_failed), null);

                    }
                });
    }

    private void startAliPlayApp(String res, onLoadDateSingleListener listener) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                try {
                    String play = AliyunManager.getInstance().play(res);
                    subscriber.onNext(play);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e.getMessage(), e);
                    }

                    @Override
                    public void onNext(String s) {
                        listener.onSuccess(s, DataType.DATA_ZERO);
                    }
                });
    }


}
