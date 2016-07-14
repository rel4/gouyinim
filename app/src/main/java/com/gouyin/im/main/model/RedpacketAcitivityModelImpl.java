package com.gouyin.im.main.model;

import com.gouyin.im.AppConstant;
import com.gouyin.im.R;
import com.gouyin.im.ServerApi;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.PayBean;
import com.gouyin.im.manager.WeixinManager;
import com.gouyin.im.manager.aliyun.AliyunManager;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.ObservableUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

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
            pay = ServerApi.getAppAPI().getredPacketIndent(playType.getType(), uid, money, authcode);
        } else if (type == 2) {
            pay = ServerApi.getAppAPI().getFlowerIndent(playType.getType(), uid, money, authcode);
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
