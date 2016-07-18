package com.moonsister.im.main.presenter;

import com.moonsister.im.AppConstant;
import com.moonsister.im.R;
import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.bean.DefaultDataBean;
import com.moonsister.im.main.model.ChangepwdActivityModel;
import com.moonsister.im.main.model.ChangepwdActivityModelImpl;
import com.moonsister.im.main.view.ChangepwdActivityView;
import com.moonsister.im.utils.StringUtis;
import com.moonsister.im.utils.UIUtils;

/**
 * Created by jb on 2016/7/11.
 */
public class ChangepwdActivityPresenterImpl implements ChangepwdActivityPresenter, BaseIModel.onLoadDateSingleListener<DefaultDataBean> {
    private ChangepwdActivityView view;
    private ChangepwdActivityModel model;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(ChangepwdActivityView changepwdActivityView) {
        this.view = changepwdActivityView;
        model = new ChangepwdActivityModelImpl();
    }

    @Override
    public void submit(String oldpwd, String newpwd) {
        view.showLoading();
        model.loadBasic(oldpwd, newpwd, this);
    }

    @Override
    public void onSuccess(DefaultDataBean bean, BaseIModel.DataType dataType) {
        view.hideLoading();
        if (bean == null) {
            view.transfePageMsg(UIUtils.getStringRes(R.string.request_failed));
        } else {
            if (StringUtis.equals(bean.getCode(), AppConstant.code_request_success)){
                view.pageFinish();
            }
            view.transfePageMsg(bean.getMsg());
        }

    }

    @Override
    public void onFailure(String msg) {
        view.hideLoading();
        view.transfePageMsg(msg);

    }
}
