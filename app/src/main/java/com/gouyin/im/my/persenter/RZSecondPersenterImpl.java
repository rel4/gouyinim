package com.gouyin.im.my.persenter;

import com.gouyin.im.AppConstant;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.my.model.RZSecondModel;
import com.gouyin.im.my.model.RZSecondModelImpl;
import com.gouyin.im.my.view.RZSecondView;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by jb on 2016/6/30.
 */
public class RZSecondPersenterImpl implements RZSecondPersenter, BaseIModel.onLoadDateSingleListener {
    private RZSecondView view;
    private RZSecondModel model;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(RZSecondView rzSecondView) {
        this.view = rzSecondView;
        model = new RZSecondModelImpl();
    }


    @Override
    public void submit(String address1, String address2, String height, String sexid, String nike, String avaterpath, ArrayList<String> pics) {
        view.showLoading();
        model.submit(address1, address2, height, sexid, nike, avaterpath, pics, this);
    }

    @Override
    public void onSuccess(Object o, BaseIModel.DataType dataType) {
        if (o != null && o instanceof DefaultDataBean) {
            DefaultDataBean bean = (DefaultDataBean) o;
            if (StringUtis.equals(AppConstant.code_request_success, bean.getCode())) {
                UIUtils.sendDelayedOneMillis(new Runnable() {
                    @Override
                    public void run() {
                        view.success();
                    }
                });

            }
            view.transfePageMsg(bean.getMsg());
        } else
            view.transfePageMsg(UIUtils.getStringRes(R.string.request_failed));
        view.hideLoading();
    }

    @Override
    public void onFailure(String msg, Throwable e) {
        view.transfePageMsg(UIUtils.getStringRes(R.string.request_failed));
        view.hideLoading();
    }
}
