package com.moonsister.im.main.model;

import com.moonsister.im.AppConstant;
import com.moonsister.im.ServerApi;
import com.moonsister.im.bean.DefaultDataBean;
import com.moonsister.im.manager.UserInfoManager;
import com.moonsister.im.utils.MD5Util;
import com.moonsister.im.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/11.
 */
public class ChangepwdActivityModelImpl implements ChangepwdActivityModel {
    @Override
    public void loadBasic(String oldpwd, String newpwd, onLoadDateSingleListener<DefaultDataBean> listener) {
        Observable<DefaultDataBean> observable = ServerApi.getAppAPI().getChangePassword(MD5Util.string2MD5(oldpwd), MD5Util.string2MD5(newpwd), UserInfoManager.getInstance().getAuthcode(), AppConstant.CHANNEL_ID);
        ObservableUtils.parser(observable, new ObservableUtils.Callback<DefaultDataBean>() {
            @Override
            public void onSuccess(DefaultDataBean bean) {
                listener.onSuccess(bean, DataType.DATA_ZERO);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }
}
