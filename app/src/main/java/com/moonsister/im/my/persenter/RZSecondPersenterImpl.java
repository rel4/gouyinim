package com.moonsister.im.my.persenter;

import com.moonsister.im.AppConstant;
import com.moonsister.im.R;
import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.bean.DefaultDataBean;
import com.moonsister.im.bean.PersonInfoDetail;
import com.moonsister.im.manager.UserInfoManager;
import com.moonsister.im.my.model.RZSecondModel;
import com.moonsister.im.my.model.RZSecondModelImpl;
import com.moonsister.im.my.view.RZSecondView;
import com.moonsister.im.utils.StringUtis;
import com.moonsister.im.utils.UIUtils;

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
                PersonInfoDetail memoryPersonInfoDetail = UserInfoManager.getInstance().getMemoryPersonInfoDetail();
                memoryPersonInfoDetail.setAttestation(2);
                UserInfoManager.getInstance().saveMemoryInstance(memoryPersonInfoDetail);
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
    public void onFailure(String msg) {
        view.transfePageMsg(UIUtils.getStringRes(R.string.request_failed));
        view.hideLoading();
    }
}
