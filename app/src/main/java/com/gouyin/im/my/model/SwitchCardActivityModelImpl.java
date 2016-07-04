package com.gouyin.im.my.model;

import com.gouyin.im.ServerApi;
import com.gouyin.im.bean.CardInfoBean;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/4.
 */
public class SwitchCardActivityModelImpl implements SwitchCardActivityModel {
    @Override
    public void loadCardInfo(onLoadDateSingleListener<CardInfoBean> listener) {
        Observable<CardInfoBean> observable = ServerApi.getAppAPI().getCardInfo(UserInfoManager.getInstance().getAuthcode());
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
