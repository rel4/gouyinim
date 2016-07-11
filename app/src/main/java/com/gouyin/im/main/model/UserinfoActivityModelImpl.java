package com.gouyin.im.main.model;

import com.gouyin.im.ServerApi;
import com.gouyin.im.bean.UserInfoChangeBean;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/11.
 */
public class UserinfoActivityModelImpl implements UserinfoActivityModel {
    @Override
    public void loadBasicData(String uid, onLoadDateSingleListener<UserInfoChangeBean> listener) {
        Observable<UserInfoChangeBean> observable = ServerApi.getAppAPI().getUserInfoBasic(uid);
        ObservableUtils.parser(observable, new ObservableUtils.Callback<UserInfoChangeBean>() {
            @Override
            public void onSuccess(UserInfoChangeBean bean) {
                listener.onSuccess(bean, DataType.DATA_ZERO);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }
}
