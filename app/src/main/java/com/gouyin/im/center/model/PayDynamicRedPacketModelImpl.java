package com.gouyin.im.center.model;

import com.gouyin.im.AppConstant;
import com.gouyin.im.R;
import com.gouyin.im.ServerApi;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.PayBean;
import com.gouyin.im.manager.WeixinManager;
import com.gouyin.im.manager.aliyun.AliyunManager;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.bean.PayRedPacketPicsBean;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.ObservableUtils;
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
    public void pay(String id, PayType type, onLoadDateSingleListener listener) {
        Observable<PayBean> observable = ServerApi.getAppAPI().redPacketPay(id, type.getType(), UserInfoManager.getInstance().getAuthcode());
        ObservableUtils.parser(observable, new ObservableUtils.Callback<PayBean>() {
            @Override
            public void onSuccess(PayBean bean) {
                if (bean.getData() == null) {
                    listener.onFailure(bean.getMsg());
                    return;
                }
                if (StringUtis.equals(bean.getCode(), AppConstant.code_request_success)) {
                    if (type == PayType.ALIPAY) {
                        startPlayApp(id, bean.getData().getAlicode(), listener);
                    } else if (type == PayType.WXPAY) {
                        WeixinManager.getInstance().pay(bean.getData());
                    } else {
                        listener.onFailure(bean.getMsg());
                    }
                } else {
                    listener.onFailure(bean.getMsg());
                }

            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }

    private void startPlayApp(String id, String res, onLoadDateSingleListener listener) {
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
        Observable<PayRedPacketPicsBean> observable = ServerApi.getAppAPI().getPayDynamicPic(id, UserInfoManager.getInstance().getAuthcode());
        ObservableUtils.parser(observable, new ObservableUtils.Callback<PayRedPacketPicsBean>() {
            @Override
            public void onSuccess(PayRedPacketPicsBean bean) {
                listener.onSuccess(bean, DataType.DATA_ZERO);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }

    @Override
    public void getPics(String id, onLoadDateSingleListener<PayRedPacketPicsBean> listener) {
        getPayDynamicPic(id, listener);
    }
}
