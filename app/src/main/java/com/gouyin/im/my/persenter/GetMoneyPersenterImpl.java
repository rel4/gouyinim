package com.gouyin.im.my.persenter;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.GetMoneyBean;
import com.gouyin.im.my.model.GetMoneyModel;
import com.gouyin.im.my.model.GetMoneyModelImpl;
import com.gouyin.im.my.view.GetMoneyView;

/**
 * Created by jb on 2016/7/3.
 */
public class GetMoneyPersenterImpl implements GetMoneyPersenter, BaseIModel.onLoadDateSingleListener<GetMoneyBean> {
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
    public void onSuccess(GetMoneyBean getMoneyBean, BaseIModel.DataType dataType) {
        view.setBasicInfo(getMoneyBean);
        view.hideLoading();

    }

    @Override
    public void onFailure(String msg) {
        view.hideLoading();
        view.transfePageMsg(msg);

    }
}
