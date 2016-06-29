package com.gouyin.im.center.widget;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.center.model.PayDynamicRedPacketModel;
import com.gouyin.im.center.model.PayDynamicRedPacketModelImpl;
import com.gouyin.im.center.view.PayDynamicRedPackketView;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

/**
 * Created by jb on 2016/6/29.
 */
public class PayDynamicRedPackketPersenterImpl implements PayDynamicRedPackketPersenter, BaseIModel.onLoadDateSingleListener<String> {
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
    public void pay(String id) {
        view.showLoading();
        model.pay(id, this);

    }

    @Override
    public void onSuccess(String code, BaseIModel.DataType dataType) {
        if (code != null) {
            if (StringUtis.equals(code, "9000"))
                view.transfePageMsg(UIUtils.getStringRes(R.string.pay_success));
            else if (StringUtis.equals(code, "8000"))
                view.transfePageMsg(UIUtils.getStringRes(R.string.pay_affirm));
            else
                view.transfePageMsg(UIUtils.getStringRes(R.string.pay_failure));
        }
        view.hideLoading();
    }

    @Override
    public void onFailure(String msg, Throwable e) {
        view.hideLoading();
        view.transfePageMsg(msg);
    }
}
