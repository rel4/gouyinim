package com.moonsister.im.login.presenter;

import com.moonsister.im.AppConstant;
import com.moonsister.im.R;
import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.bean.BaseBean;
import com.moonsister.im.login.model.FindPasswordNextActivityModel;
import com.moonsister.im.login.model.FindPasswordNextActivityModelImpl;
import com.moonsister.im.login.view.FindPasswordNextActivityView;
import com.moonsister.im.utils.StringUtis;
import com.moonsister.im.utils.UIUtils;

/**
 * Created by jb on 2016/7/11.
 */
public class FindPasswordNextActivityPresenterImpl implements FindPasswordNextActivityPresenter, BaseIModel.onLoadDateSingleListener<BaseBean> {
    private FindPasswordNextActivityView view;
    private FindPasswordNextActivityModel model;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(FindPasswordNextActivityView findPasswordNextActivityView) {
        this.view = findPasswordNextActivityView;
        model = new FindPasswordNextActivityModelImpl();
    }

    @Override
    public void submit(String newpwd, String code) {
        view.showLoading();
        model.submit(newpwd, code, this);
    }

    @Override
    public void onSuccess(BaseBean bean, BaseIModel.DataType dataType) {
        if (bean == null) {
            view.hideLoading();
            view.transfePageMsg(UIUtils.getStringRes(R.string.request_failed));
        } else {
            if (StringUtis.equals(bean.getCode(), AppConstant.code_request_success)) {
                UIUtils.sendDelayedOneMillis(new Runnable() {
                    @Override
                    public void run() {
                        view.pageFinish();
                    }
                });
            }
            view.transfePageMsg(bean.getMsg());
            view.hideLoading();
        }
    }

    @Override
    public void onFailure(String msg) {
        view.transfePageMsg(msg);
        view.hideLoading();
    }
}
