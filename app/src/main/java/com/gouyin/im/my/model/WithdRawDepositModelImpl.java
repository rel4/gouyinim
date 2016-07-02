package com.gouyin.im.my.model;

import com.gouyin.im.ServerApi;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/2.
 */
public class WithdRawDepositModelImpl implements WithdRawDepositModel {
    @Override
    public void loadEnableMoney(onLoadDateSingleListener<DefaultDataBean> listener) {
        Observable<DefaultDataBean> observable = ServerApi.getAppAPI().getEnableMoney(UserInfoManager.getInstance().getAuthcode());
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
