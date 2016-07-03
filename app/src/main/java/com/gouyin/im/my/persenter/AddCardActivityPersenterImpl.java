package com.gouyin.im.my.persenter;

import com.gouyin.im.AppConstant;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.my.model.AddCardActivityModel;
import com.gouyin.im.my.model.AddCardActivityModelImpl;
import com.gouyin.im.my.view.AddCardActivityView;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

/**
 * Created by jb on 2016/7/3.
 */
public class AddCardActivityPersenterImpl implements AddCardActivityPersenter, BaseIModel.onLoadDateSingleListener<DefaultDataBean> {
    private AddCardActivityView view;
    private AddCardActivityModel model;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(AddCardActivityView addCardActivityView) {
        this.view = addCardActivityView;
        model = new AddCardActivityModelImpl();

    }

    @Override
    public void submitAccount(String cardNumber, String username, String cardType, String bankname) {
        view.showLoading();
        model.submitAccount(cardNumber, username, cardType, bankname, this);
    }


    @Override
    public void onSuccess(DefaultDataBean defaultDataBean, BaseIModel.DataType dataType) {
        view.hideLoading();
        if (StringUtis.equals(defaultDataBean.getCode(), AppConstant.code_request_success)) {
            RxBus.getInstance().send(Events.EventEnum.CARD_CHANGE, null);
            UIUtils.sendDelayedOneMillis(new Runnable() {
                @Override
                public void run() {
                    view.pageFinish();
                }
            });

        }
        view.transfePageMsg(defaultDataBean.getMsg());
    }

    @Override
    public void onFailure(String msg) {
        view.transfePageMsg(msg);
        view.hideLoading();
    }
}
