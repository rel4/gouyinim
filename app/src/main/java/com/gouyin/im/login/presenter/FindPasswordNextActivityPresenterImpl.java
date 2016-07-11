package com.gouyin.im.login.presenter;

import com.gouyin.im.AppConstant;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.login.model.FindPasswordNextActivityModel;
import com.gouyin.im.login.model.FindPasswordNextActivityModelImpl;
import com.gouyin.im.login.view.FindPasswordNextActivityView;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

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

        }
    }

    @Override
    public void onFailure(String msg) {

    }
}
