package com.moonsister.tcjy.main.model;

import com.moonsister.tcjy.AppConstant;
import com.moonsister.tcjy.ServerApi;
import com.moonsister.tcjy.bean.PayBean;
import com.moonsister.tcjy.manager.WeixinManager;
import com.moonsister.tcjy.manager.aliyun.AliyunManager;
import com.moonsister.tcjy.manager.UserInfoManager;
import com.moonsister.tcjy.utils.ObservableUtils;
import com.moonsister.tcjy.utils.StringUtis;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/6/24.
 */
public class RedpacketAcitivityModelImpl implements RedpacketAcitivityModel {


    @Override
    public void aliPay(int type, PayType playType, String uid, String money, onLoadDateSingleListener listener) {
        String authcode = UserInfoManager.getInstance().getAuthcode();
        Observable<PayBean> pay = null;
        if (type == 1) {
            pay = ServerApi.getAppAPI().getredPacketIndent(playType.getType(), uid, money, authcode,AppConstant.CHANNEL_ID);
        } else if (type == 2) {
            pay = ServerApi.getAppAPI().getFlowerIndent(playType.getType(), uid, money, authcode,AppConstant.CHANNEL_ID);
        }
        if (pay != null) {
            ObservableUtils.parser(pay, new ObservableUtils.Callback<PayBean>() {
                @Override
                public void onSuccess(PayBean bean) {
                    if (StringUtis.equals(bean.getCode(), AppConstant.code_request_success) && bean.getData() != null) {
                        PayBean.DataBean data = bean.getData();
                        startAliPlayApp(data, playType, listener);
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


    }

    private void startAliPlayApp(PayBean.DataBean data, PayType playType, onLoadDateSingleListener listener) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                try {
                    if (playType == PayType.ALIPAY) {
                        String play = AliyunManager.getInstance().play(data.getAlicode());
                        subscriber.onNext(play);
                    }else if (playType==PayType.WXPAY){
                        WeixinManager.getInstance().pay(data);
                    }
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
