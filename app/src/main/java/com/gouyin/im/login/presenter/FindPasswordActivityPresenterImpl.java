package com.gouyin.im.login.presenter;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.RegiterBean;
import com.gouyin.im.login.model.FindPasswordActivityModel;
import com.gouyin.im.login.model.FindPasswordActivityModelImpl;
import com.gouyin.im.main.view.FindPasswordActivityView;
import com.gouyin.im.utils.ConfigUtils;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;

/**
 * Created by jb on 2016/7/11.
 */
public class FindPasswordActivityPresenterImpl implements FindPasswordActivityPresenter, BaseIModel.onLoadDateSingleListener {
    private FindPasswordActivityView view;
    private FindPasswordActivityModel model;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(FindPasswordActivityView findPasswordActivityView) {
        this.view = findPasswordActivityView;
        model = new FindPasswordActivityModelImpl();
    }

    @Override
    public void getSecurityCode(String phoneNumber) {
        view.showLoading();
        model.getSecurityCode(phoneNumber, this);
    }

    @Override
    public void submitRegiter(String phone, String code) {
        view.showLoading();
        model.submit(phone, code, this);
    }

    @Override
    public void onSuccess(Object bean, BaseIModel.DataType dataType) {
        if (bean == null) {
            view.hideLoading();
            view.transfePageMsg(UIUtils.getStringRes(R.string.request_failed));
            return;
        }
        switch (dataType) {
            case DATA_ZERO:

                if (bean != null) {
                    if ("1".equals(((BaseBean) bean).getCode()))
                        view.LoopMsg();
                    view.transfePageMsg(((BaseBean) bean).getMsg());
                }
                break;
            case DATA_ONE:
                RegiterBean rb = (RegiterBean) bean;
                if ("1".equals(rb.getCode()))
                        ConfigUtils.getInstance().getMainHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LogUtils.e(RegiterFragmentPresenerImpl.class, "RegiterCode  : " + rb.getData().toString());
                                view.navigationNext(rb.getData().getAuthcode());
                            }
                        }, 1000);



                break;
        }
        view.hideLoading();

    }

    @Override
    public void onFailure(String msg) {
        view.transfePageMsg(msg);
        view.hideLoading();

    }
}
