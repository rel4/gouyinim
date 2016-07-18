package com.moonsister.im.my.model;

import com.moonsister.im.AppConstant;
import com.moonsister.im.ServerApi;
import com.moonsister.im.bean.CardInfoBean;
import com.moonsister.im.manager.UserInfoManager;
import com.moonsister.im.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/4.
 */
public class SwitchCardActivityModelImpl implements SwitchCardActivityModel {
    @Override
    public void loadCardInfo(onLoadDateSingleListener<CardInfoBean> listener) {
        Observable<CardInfoBean> observable = ServerApi.getAppAPI().getCardInfo(UserInfoManager.getInstance().getAuthcode(), AppConstant.CHANNEL_ID);
        ObservableUtils.parser(observable, new ObservableUtils.Callback<CardInfoBean>() {
            @Override
            public void onSuccess(CardInfoBean bean) {
                listener.onSuccess(bean, DataType.DATA_ZERO);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }
}
