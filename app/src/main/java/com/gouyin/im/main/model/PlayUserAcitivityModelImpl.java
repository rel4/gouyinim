package com.gouyin.im.main.model;

import com.gouyin.im.AppConstant;
import com.gouyin.im.R;
import com.gouyin.im.ServerApi;
import com.gouyin.im.aliyun.AliyunManager;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/6/24.
 */
public class PlayUserAcitivityModelImpl implements PlayUserAcitivityModel {


    @Override
    public void aliPay(int type, PayType playType, String uid, String money, onLoadDateSingleListener listener) {
        String authcode = UserInfoManager.getInstance().getAuthcode();
        Observable<DefaultDataBean> pay = null;
        if (type == 1) {
            pay = ServerApi.getAppAPI().getredPacketIndent(playType.getType(), uid, money, authcode);
        } else if (type == 2) {
            pay = ServerApi.getAppAPI().getFlowerIndent(playType.getType(), uid, money, authcode);
        }
        if (pay != null) {
            pay.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<DefaultDataBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            listener.onFailure(UIUtils.getStringRes(R.string.request_failed));
                            LogUtils.e(PlayUserAcitivityModelImpl.this, " onError " + e.getMessage());
                        }

                        @Override
                        public void onNext(DefaultDataBean defaultDataBean) {
                            if (defaultDataBean == null) {
                                listener.onFailure(UIUtils.getStringRes(R.string.request_failed));
                            } else {
                                if (StringUtis.equals(defaultDataBean.getCode(), AppConstant.code_request_success)) {
                                    Object obj = defaultDataBean.getObj();
                                    LogUtils.e(PlayUserAcitivityModelImpl.this, " defaultDataBean " + obj);
                                    startAliPlayApp((String) obj, listener);
                                } else {
                                    listener.onFailure(defaultDataBean.getMsg());
                                }

                            }

                        }
                    });

        }


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
                        listener.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        listener.onSuccess(s, DataType.DATA_ZERO);
                    }
                });
    }


}
