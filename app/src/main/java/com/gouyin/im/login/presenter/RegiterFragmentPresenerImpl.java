package com.gouyin.im.login.presenter;


import com.gouyin.im.R;
import com.gouyin.im.base.onLoadDateListener;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.login.model.RegiterFragmentModel;
import com.gouyin.im.login.model.RegiterFragmentModelImpl;
import com.gouyin.im.login.view.RegiterFragmentView;
import com.gouyin.im.utils.ConfigUtils;

/**
 * Created by pc on 2016/6/14.
 */
public class RegiterFragmentPresenerImpl implements RegiterFragmentPresener<RegiterFragmentView>, onLoadDateListener<BaseBean>, RegiterFragmentModel.onLoadSubmitListenter<BaseBean> {
    private RegiterFragmentView view;
    private RegiterFragmentModel fragmentModel;

    @Override
    public void getSecurityCode(String phoneNumer) {
        view.showLoading();
        fragmentModel.loadSecurity(phoneNumer, this);

    }

    @Override
    public void submitRegiter(String phoneNumber, String code) {
        view.showLoading();
        fragmentModel.loadSubmit(phoneNumber, code, this);

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(RegiterFragmentView regiterFragmentView) {
        this.view = regiterFragmentView;
        fragmentModel = new RegiterFragmentModelImpl();
    }


    @Override
    public void onSuccess(BaseBean baseBean) {
        view.hideLoading();
        if (baseBean != null) {
            if ("1".equals(baseBean.getCode()))
                view.LoopMsg();
            view.requestFailed(baseBean.getMsg());
        }
    }

    @Override
    public void onFailure(String msg, Exception e) {
        view.hideLoading();
        view.requestFailed(msg);
    }


    @Override
    public void onSubmitSuccess(BaseBean baseBean) {
        view.hideLoading();
        if (baseBean != null) {
            view.requestFailed(baseBean.getMsg());
            if ("1".equals(baseBean.getCode()))
                ConfigUtils.getInstance().getMainHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.navigationNext();
                    }
                },1000);



        }


    }

    @Override
    public void onSubmitFailure(String msg, Exception e) {
        view.hideLoading();
        view.requestFailed(ConfigUtils.getInstance().getResources().getString(R.string.request_failed));
    }

}
