package com.gouyin.im.center.model;

import com.gouyin.im.R;
import com.gouyin.im.ServerApi;
import com.gouyin.im.aliyun.AliyunManager;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.bean.PayRedPacketPicsBean;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/6/29.
 */
public class PayDynamicRedPacketModelImpl implements PayDynamicRedPacketModel {
    @Override
    public void pay(String id, onLoadDateSingleListener listener) {
        ServerApi.getAppAPI().redPacketPay(id, UserInfoManager.getInstance().getAuthcode())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DefaultDataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(PayDynamicRedPacketModelImpl.this, " onError " + e.getMessage());
                    }

                    @Override
                    public void onNext(DefaultDataBean defaultDataBean) {
                        LogUtils.e(PayDynamicRedPacketModelImpl.this, " onNext " + defaultDataBean.toString());
                        if (defaultDataBean != null && defaultDataBean.getObj() instanceof String) {
                            String res = (String) defaultDataBean.getObj();
                            LogUtils.e(PayDynamicRedPacketModelImpl.this, " defaultDataBean " + res);
                            startAliPlayApp(id, res, listener);
                        } else
                            listener.onFailure(UIUtils.getStringRes(R.string.request_failed));

                    }
                });
    }

    private void startAliPlayApp(String id, String res, onLoadDateSingleListener listener) {
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
                        if (s != null) {
                            if (StringUtis.equals(s, "9000"))
                                getPayDynamicPic(id, listener);
                            else if (StringUtis.equals(s, "8000"))
                                getPayDynamicPic(id, listener);
                            else
//
                                listener.onFailure(UIUtils.getStringRes(R.string.pay_failure));
//                                view.transfePageMsg(UIUtils.getStringRes(R.string.pay_failure));
                        } else
                            listener.onFailure(UIUtils.getStringRes(R.string.request_failed));
                    }
                });
    }

    private void getPayDynamicPic(String id, onLoadDateSingleListener listener) {
        ServerApi.getAppAPI().getPayDynamicPic(id, UserInfoManager.getInstance().getAuthcode())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PayRedPacketPicsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(UIUtils.getStringRes(R.string.request_failed));
                    }

                    @Override
                    public void onNext(PayRedPacketPicsBean bean) {
                        if (bean != null) {
                            listener.onSuccess(bean, DataType.DATA_ZERO);
                        } else
                            listener.onFailure(UIUtils.getStringRes(R.string.request_failed));

                    }
                });
    }
}
