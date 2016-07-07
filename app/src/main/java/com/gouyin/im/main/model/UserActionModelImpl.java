package com.gouyin.im.main.model;

import com.gouyin.im.ServerApi;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/7.
 */
public class UserActionModelImpl implements UserActionModel {

    @Override
    public void wacthAction(String uid, String type, onLoadDateSingleListener listener) {
        Observable<DefaultDataBean> observable = ServerApi.getAppAPI().getWacthAction(uid, type, UserInfoManager.getInstance().getAuthcode());
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
