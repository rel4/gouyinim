package com.gouyin.im.main.presenter;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.main.model.PlayUserAcitivityModel;
import com.gouyin.im.main.model.PlayUserAcitivityModelImpl;
import com.gouyin.im.main.view.PlayUserAcitivityView;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

/**
 * Created by jb on 2016/6/24.
 */
public class RedpacketAcitivityPresenterImpl implements RedpacketAcitivityPresenter, BaseIModel.onLoadDateSingleListener<String> {
    private PlayUserAcitivityView view;
    private PlayUserAcitivityModel model;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(PlayUserAcitivityView baseIView) {
        this.view = baseIView;
        model = new PlayUserAcitivityModelImpl();

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
        model.aliPay(type, PlayUserAcitivityModel.PayType.ALIPAY, uid, money, this);
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
