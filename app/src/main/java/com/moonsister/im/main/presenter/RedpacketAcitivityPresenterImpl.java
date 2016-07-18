package com.moonsister.im.main.presenter;

import com.moonsister.im.R;
import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.event.Events;
import com.moonsister.im.event.RxBus;
import com.moonsister.im.main.model.RedpacketAcitivityModel;
import com.moonsister.im.main.model.RedpacketAcitivityModelImpl;
import com.moonsister.im.main.view.PlayUserAcitivityView;
import com.moonsister.im.utils.StringUtis;
import com.moonsister.im.utils.UIUtils;

/**
 * Created by jb on 2016/6/24.
 */
public class RedpacketAcitivityPresenterImpl implements RedpacketAcitivityPresenter, BaseIModel.onLoadDateSingleListener<String> {
    private PlayUserAcitivityView view;
    private RedpacketAcitivityModel model;
    private RedpacketAcitivityModel.PayType payType;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(PlayUserAcitivityView baseIView) {
        this.view = baseIView;
        model = new RedpacketAcitivityModelImpl();

    }

    @Override
    public void swicthAction(int id) {
        switch (id) {
            case R.id.action_back:
                view.pageFinish();
                break;
            case R.id.tv_weixin_play:
                view.weixinPay();
                break;
            case R.id.tv_aliplay_play:
                view.swicthAliPlay();
                break;
        }
    }

    private String moneyNumber;

    @Override
    public void aliPay(int type, String uid, String money) {
        view.showLoading();
        moneyNumber = money;
        payType = RedpacketAcitivityModel.PayType.ALIPAY;
        model.aliPay(type, payType, uid, money, this);
    }

    @Override
    public void weixinPay(int type, String uid, String money) {
        view.showLoading();
        moneyNumber = money;
        payType = RedpacketAcitivityModel.PayType.WXPAY;
        model.aliPay(type, payType, uid, money, this);
    }

    @Override
    public void onSuccess(String o, BaseIModel.DataType dataType) {
        view.hideLoading();
        if (StringUtis.equals("9000", o)) {
            Events<String> events = new Events<String>();
            events.what = Events.EventEnum.CHAT_SEND_REDPACKET_SUCCESS;
            events.message = moneyNumber;
            RxBus.getInstance().send(events);
            view.transfePageMsg(UIUtils.getStringRes(R.string.pay_success));
            UIUtils.sendDelayedOneMillis(new Runnable() {
                @Override
                public void run() {
                    view.pageFinish();
                }
            });
        } else if (StringUtis.equals("8000", o))
            view.transfePageMsg(UIUtils.getStringRes(R.string.pay_affirm));
        else view.transfePageMsg(UIUtils.getStringRes(R.string.pay_failure));


    }

    @Override
    public void onFailure(String msg) {
        view.hideLoading();
        view.transfePageMsg(UIUtils.getStringRes(R.string.request_failed));
    }
}
