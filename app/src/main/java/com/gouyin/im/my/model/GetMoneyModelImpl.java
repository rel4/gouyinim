package com.gouyin.im.my.model;

import com.gouyin.im.ServerApi;
import com.gouyin.im.bean.GetMoneyBean;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/3.
 */
public class GetMoneyModelImpl implements GetMoneyModel {
    @Override
    public void loadbasicInfo(onLoadDateSingleListener<GetMoneyBean> listener) {
        Observable<GetMoneyBean> observable = ServerApi.getAppAPI().getGetmoney(UserInfoManager.getInstance().getAuthcode());
        ObservableUtils.parser(observable, new ObservableUtils.Callback<GetMoneyBean>() {
            @Override
            public void onSuccess(GetMoneyBean bean) {
                listener.onSuccess(bean, DataType.DATA_ZERO);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }
}
