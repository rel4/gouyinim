package com.moonsister.im.my.model;

import com.moonsister.im.AppConstant;
import com.moonsister.im.ServerApi;
import com.moonsister.im.bean.DefaultDataBean;
import com.moonsister.im.manager.UserInfoManager;
import com.moonsister.im.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/2.
 */
public class WithdRawDepositModelImpl implements WithdRawDepositModel {
    @Override
    public void loadEnableMoney(onLoadDateSingleListener<DefaultDataBean> listener) {
        Observable<DefaultDataBean> observable = ServerApi.getAppAPI().getEnableMoney(UserInfoManager.getInstance().getAuthcode(), AppConstant.CHANNEL_ID);
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
