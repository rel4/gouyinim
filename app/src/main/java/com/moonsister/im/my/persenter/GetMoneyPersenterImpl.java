package com.moonsister.im.my.persenter;

import com.moonsister.im.AppConstant;
import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.bean.BaseBean;
import com.moonsister.im.bean.GetMoneyBean;
import com.moonsister.im.event.Events;
import com.moonsister.im.event.RxBus;
import com.moonsister.im.my.model.GetMoneyModel;
import com.moonsister.im.my.model.GetMoneyModelImpl;
import com.moonsister.im.my.view.GetMoneyView;
import com.moonsister.im.utils.StringUtis;
import com.moonsister.im.utils.UIUtils;

/**
 * Created by jb on 2016/7/3.
 */
public class GetMoneyPersenterImpl implements GetMoneyPersenter, BaseIModel.onLoadDateSingleListener<BaseBean> {
    private GetMoneyView view;
    private GetMoneyModel model;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(GetMoneyView getMoneyView) {
        this.view = getMoneyView;
        model = new GetMoneyModelImpl();

    }

    @Override
    public void loadbasicInfo() {
        view.showLoading();
        model.loadbasicInfo(this);
    }

    @Override
    public void PaySubmit(String number, int money) {
        view.showLoading();
        model.paySubmit(number, money, this);
    }

    @Override
    public void onSuccess(BaseBean bean, BaseIModel.DataType dataType) {
        view.hideLoading();
        if (bean == null) {
            return;
        }

        switch (dataType) {
            case DATA_ZERO:
                view.setBasicInfo((GetMoneyBean) bean);
                break;
            case DATA_ONE:
                if (StringUtis.equals(bean.getCode(), AppConstant.code_request_success)) {
                    RxBus.getInstance().send(Events.EventEnum.MONEY_CHANGE, null);
                    UIUtils.sendDelayedOneMillis(new Runnable() {
                        @Override
                        public void run() {
                            view.pageFinish();
                        }
                    });
                }
                break;
        }
        view.transfePageMsg(bean.getMsg());

    }

    @Override
    public void onFailure(String msg) {
        view.hideLoading();
        view.transfePageMsg(msg);

    }
}
