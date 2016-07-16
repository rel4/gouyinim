package com.gouyin.im.center.presenter;

import com.gouyin.im.AppConstant;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.PayRedPacketPicsBean;
import com.gouyin.im.center.model.PayDynamicRedPacketModel;
import com.gouyin.im.center.model.PayDynamicRedPacketModelImpl;
import com.gouyin.im.center.presenter.PayDynamicRedPackketPersenter;
import com.gouyin.im.center.view.PayDynamicRedPackketView;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.main.model.RedpacketAcitivityModel;
import com.gouyin.im.main.widget.MainActivity;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

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
//        if (code != null) {
//            if (StringUtis.equals(code, "9000"))
//                view.transfePageMsg(UIUtils.getStringRes(R.string.pay_success));
//            else if (StringUtis.equals(code, "8000"))
//                view.transfePageMsg(UIUtils.getStringRes(R.string.pay_affirm));
//            else
//                view.transfePageMsg(UIUtils.getStringRes(R.string.pay_failure));
//        }
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

        view.hideLoading();
    }

    @Override
    public void onFailure(String msg) {
        view.hideLoading();
        view.transfePageMsg(msg);
    }
}
