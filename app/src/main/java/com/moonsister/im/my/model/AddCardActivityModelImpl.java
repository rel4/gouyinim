package com.moonsister.im.my.model;

import com.moonsister.im.AppConstant;
import com.moonsister.im.ServerApi;
import com.moonsister.im.bean.DefaultDataBean;
import com.moonsister.im.manager.UserInfoManager;
import com.moonsister.im.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/3.
 */
public class AddCardActivityModelImpl implements AddCardActivityModel {
    @Override
    public void submitAccount(String cardNumber, String username, String cardType, String bankname, onLoadDateSingleListener listener) {
        Observable<DefaultDataBean> observable = ServerApi.getAppAPI().getsubmitAccount(cardNumber, username, cardType, bankname, UserInfoManager.getInstance().getAuthcode(), AppConstant.CHANNEL_ID);
        ObservableUtils.parser(observable, new ObservableUtils.Callback<DefaultDataBean>() {
            @Override
            public void onSuccess(DefaultDataBean defaultDataBean) {
                listener.onSuccess(defaultDataBean, DataType.DATA_ZERO);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }
}
