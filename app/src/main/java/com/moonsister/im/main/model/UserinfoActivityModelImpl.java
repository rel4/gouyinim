package com.moonsister.im.main.model;

import com.moonsister.im.AppConstant;
import com.moonsister.im.ServerApi;
import com.moonsister.im.bean.UserInfoChangeBean;
import com.moonsister.im.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/11.
 */
public class UserinfoActivityModelImpl implements UserinfoActivityModel {
    @Override
    public void loadBasicData(String uid, onLoadDateSingleListener<UserInfoChangeBean> listener) {
        Observable<UserInfoChangeBean> observable = ServerApi.getAppAPI().getUserInfoBasic(uid, AppConstant.CHANNEL_ID);
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
