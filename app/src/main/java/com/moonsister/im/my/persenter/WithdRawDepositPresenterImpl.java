package com.moonsister.im.my.persenter;

import com.moonsister.im.AppConstant;
import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.bean.DefaultDataBean;
import com.moonsister.im.my.model.WithdRawDepositModelImpl;
import com.moonsister.im.my.model.WithdRawDepositModel;
import com.moonsister.im.my.view.WithdRawDepositView;
import com.moonsister.im.utils.StringUtis;

/**
 * Created by jb on 2016/7/2.
 */
public class WithdRawDepositPresenterImpl implements WithdRawDepositPresenter, BaseIModel.onLoadDateSingleListener<DefaultDataBean> {
    private WithdRawDepositView view;
    private WithdRawDepositModel model;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(WithdRawDepositView withdRawDepositView) {
        this.view = withdRawDepositView;
        model = new WithdRawDepositModelImpl();
    }

    @Override
    public void loadEnableMoney() {
        view.showLoading();
        model.loadEnableMoney(this);
    }

    @Override
    public void onSuccess(DefaultDataBean defaultDataBean, BaseIModel.DataType dataType) {
        if (!StringUtis.equals(defaultDataBean.getCode(), AppConstant.code_request_success)) {
            view.transfePageMsg(defaultDataBean.getMsg());
            view.hideLoading();
            return;
        }
        if (defaultDataBean.getObj() != null && defaultDataBean.getObj() instanceof String) {

            view.setloadEnableMoney((String)defaultDataBean.getObj());
        }
        view.hideLoading();

    }

    @Override
    public void onFailure(String msg) {
        view.transfePageMsg(msg);
        view.hideLoading();
    }
}
