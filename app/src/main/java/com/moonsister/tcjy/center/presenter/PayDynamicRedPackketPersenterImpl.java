package com.moonsister.tcjy.center.presenter;

import com.moonsister.tcjy.AppConstant;
import com.moonsister.tcjy.R;
import com.moonsister.tcjy.base.BaseIModel;
import com.moonsister.tcjy.bean.PayRedPacketPicsBean;
import com.moonsister.tcjy.center.model.PayDynamicRedPacketModel;
import com.moonsister.tcjy.center.model.PayDynamicRedPacketModelImpl;
import com.moonsister.tcjy.center.view.PayDynamicRedPackketView;
import com.moonsister.tcjy.event.Events;
import com.moonsister.tcjy.event.RxBus;
import com.moonsister.tcjy.utils.StringUtis;
import com.moonsister.tcjy.utils.UIUtils;

/**
 * Created by jb on 2016/6/29.
 */
public class PayDynamicRedPackketPersenterImpl implements PayDynamicRedPackketPersenter, BaseIModel.onLoadDateSingleListener<PayRedPacketPicsBean> {
    private PayDynamicRedPackketView view;
    private PayDynamicRedPacketModel model;


    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(PayDynamicRedPackketView payDynamicRedPackketView) {
        this.view = payDynamicRedPackketView;
        model = new PayDynamicRedPacketModelImpl();
    }

    @Override
    public void alipay(String id) {
        view.showLoading();
        model.pay(id, PayDynamicRedPacketModel.PayType.ALIPAY, this);

    }

    @Override
    public void weixinPay(String id) {
        view.showLoading();
        model.pay(id, PayDynamicRedPacketModel.PayType.WXPAY, this);
    }

    @Override
    public void getPics(String id) {
        view.showLoading();
        model.getPics(id, this);
    }

    @Override
    public void onSuccess(PayRedPacketPicsBean bean, BaseIModel.DataType dataType) {

        switch (dataType) {
            case DATA_ZERO:
                if (bean == null) {
                    view.transfePageMsg(UIUtils.getStringRes(R.string.request_failed));
                } else {
                    if (StringUtis.equals(AppConstant.code_request_success, bean.getCode())) {
                        Events<PayRedPacketPicsBean> events = new Events<PayRedPacketPicsBean>();
                        events.what = Events.EventEnum.PAY_SUCCESS_GET_DATA;
                        events.message = bean;
                        RxBus.getInstance().send(events);
                        view.finishPage();
                    }
                    view.transfePageMsg(bean.getMsg());

                }
                break;
            case DATA_ONE:
                break;
        }
        view.hideLoading();
    }

    @Override
    public void onFailure(String msg) {
        view.hideLoading();
        view.transfePageMsg(msg);
    }
}
