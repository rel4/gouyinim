package com.gouyin.im.login.presenter;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.login.model.RegiterDataFragmentModel;
import com.gouyin.im.login.model.RegiterDataFragmentModelImpl;
import com.gouyin.im.login.view.RegiterDataFragmentView;
import com.gouyin.im.utils.ConfigUtils;
import com.gouyin.im.utils.LogUtils;

/**
 * Created by jb on 2016/6/15.
 */
public class RegiterDataFragmentPresenterImpl implements RegiterDataFragmentPresenter, BaseIModel.onLoadDateListener<BaseBean> {

    private RegiterDataFragmentView view;
    private RegiterDataFragmentModel model;

    @Override
    public void login(String face, String sex, String pwd, String authcode) {
        view.showLoading();
        LogUtils.e(this, "login  : "+face + " sex " +
                sex + " pwd" +
                pwd + " authcode " +
                authcode + " ");
        model.login(face, sex, pwd, authcode, this);

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(RegiterDataFragmentView regiterDataFragmentView) {
        this.view = regiterDataFragmentView;
        model = new RegiterDataFragmentModelImpl();
    }

    @Override
    public void onSuccess(BaseBean baseBean) {

        if (baseBean != null) {
            LogUtils.e(this,"msg : "+baseBean.getMsg());
            view.requestFailed(baseBean.getMsg());
            if ("1".equals(baseBean.getCode())) {
                view.navigationNext();
            }
        }
        view.hideLoading();

    }

    @Override
    public void onFailure(String msg, Throwable e) {
        LogUtils.e(this, "Throwable : " + msg);
        view.requestFailed(ConfigUtils.getInstance().getResources().getString(R.string.net_Exception));
        view.hideLoading();
    }
}
