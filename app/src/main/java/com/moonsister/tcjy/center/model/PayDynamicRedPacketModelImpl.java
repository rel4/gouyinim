package com.moonsister.tcjy.center.model;

import com.moonsister.pay.aibeipay.AiBeiPayManager;
import com.moonsister.pay.aliyun.AliPayManager;
import com.moonsister.tcjy.AppConstant;
import com.moonsister.tcjy.R;
import com.moonsister.tcjy.ServerApi;
import com.moonsister.pay.tencent.PayBean;
import com.moonsister.tcjy.bean.PayRedPacketPicsBean;
import com.moonsister.tcjy.event.Events;
import com.moonsister.tcjy.event.RxBus;
import com.moonsister.tcjy.manager.UserInfoManager;
import com.moonsister.pay.tencent.WeixinManager;
import com.moonsister.tcjy.utils.ConfigUtils;
import com.moonsister.tcjy.utils.ObservableUtils;
import com.moonsister.tcjy.utils.StringUtis;
import com.moonsister.tcjy.utils.UIUtils;
import com.moonsister.tcjy.wxapi.WXPayEntryActivity;

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
        Observable<PayBean> observable = ServerApi.getAppAPI().redPacketPay(id, type.getType(), UserInfoManager.getInstance().getAuthcode(), AppConstant.CHANNEL_ID);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PayBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(UIUtils.getStringRes(R.string.network_error));
                    }

                    @Override
                    public void onNext(PayBean payBean) {

                        if (payBean == null) {
                            listener.onFailure(UIUtils.getStringRes(R.string.request_failed));
                            return;
                        }
                        if (StringUtis.equals("1000", payBean.getCode())) {
                            listener.onFailure(UIUtils.getStringRes(R.string.code_timeout));
                            RxBus.getInstance().send(Events.EventEnum.LOGIN_CODE_TIMEOUT, null);
                            return;
                        }
                        if (!StringUtis.equals(AppConstant.code_request_success, payBean.getCode())) {
                            listener.onFailure(payBean.getMsg());
                            return;
                        }


                        if (payBean.getData() == null) {
                            listener.onFailure(payBean.getMsg());
                            return;
                        }
                        if (StringUtis.equals(payBean.getCode(), AppConstant.code_request_success)) {
                            if (type == PayType.ALI_PAY) {
                                startPlayApp(id, payBean.getData().getAlicode(), listener);
                            } else if (type == PayType.WX_PAY) {
                                WeixinManager.getInstance(ConfigUtils.getInstance().getApplicationContext(), WXPayEntryActivity.APP_ID).pay(payBean.getData());
                                listener.onSuccess(null, DataType.DATA_ONE);
                            } else if (type == PayType.IAPP_PAY) {

                                AiBeiPayManager.getInstance().pay(ConfigUtils.getInstance().getActivityContext(),  payBean.getData().getAbcode(), new AiBeiPayManager.AiBeiResultCallback() {
                                    @Override
                                    public void onPayResult(int resultCode, String resultInfo) {
                                        if (resultCode == 1) {
//                                            listener.onSuccess(resultCode + "", DataType.DATA_TWO);
                                            getPayDynamicPic(id, listener, DataType.DATA_ZERO);
                                        } else if (resultCode == 4) {
                                            listener.onFailure(resultInfo);
                                        } else {
                                            listener.onFailure(UIUtils.getStringRes(R.string.pay_failure));
                                        }
                                    }
                                });

                            } else {
                                listener.onFailure(payBean.getMsg());
                            }
                        } else {
                            listener.onFailure(payBean.getMsg());
                        }


                    }
                });


//        ObservableUtils.parser(observable, new ObservableUtils.Callback<PayBean>() {
//            @Override
//            public void onSuccess(PayBean bean) {
//                if (bean.getData() == null) {
//                    listener.onFailure(bean.getMsg());
//                    return;
//                }
//                if (StringUtis.equals(bean.getCode(), AppConstant.code_request_success)) {
//                    if (type == PayType.ALIPAY) {
//                        startPlayApp(id, bean.getData().getAlicode(), listener);
//                    } else if (type == PayType.WXPAY) {
//                        WeixinManager.getInstance(ConfigUtils.getInstance().getApplicationContext(), WXPayEntryActivity.APP_ID).pay(bean.getData());
//                    } else {
//                        listener.onFailure(bean.getMsg());
//                    }
//                } else {
//                    listener.onFailure(bean.getMsg());
//                }
//
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                listener.onFailure(msg);
//            }
//        });
    }

    private void startPlayApp(String id, String res, onLoadDateSingleListener listener) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                try {
                    String play = AliPayManager.getInstance().play(ConfigUtils.getInstance().getActivityContext(), res);
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
                                getPayDynamicPic(id, listener, DataType.DATA_ZERO);
                            else if (StringUtis.equals(s, "8000"))
                                getPayDynamicPic(id, listener, DataType.DATA_ZERO);
                            else
                                listener.onFailure(UIUtils.getStringRes(R.string.pay_failure));
                        } else
                            listener.onFailure(UIUtils.getStringRes(R.string.request_failed));
                    }
                });
    }

    private void getPayDynamicPic(String id, onLoadDateSingleListener listener, DataType type) {
        Observable<PayRedPacketPicsBean> observable = ServerApi.getAppAPI().getPayDynamicPic(id, UserInfoManager.getInstance().getAuthcode(), AppConstant.CHANNEL_ID);
        ObservableUtils.parser(observable, new ObservableUtils.Callback<PayRedPacketPicsBean>() {
            @Override
            public void onSuccess(PayRedPacketPicsBean bean) {
                listener.onSuccess(bean, type);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }

    @Override
    public void getPics(String id, onLoadDateSingleListener<PayRedPacketPicsBean> listener) {
        getPayDynamicPic(id, listener, DataType.DATA_ZERO);
    }
}
