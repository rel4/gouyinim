package com.moonsister.im.my.model;

import com.moonsister.im.AppConstant;
import com.moonsister.im.ServerApi;
import com.moonsister.im.bean.DefaultDataBean;
import com.moonsister.im.bean.GetMoneyBean;
import com.moonsister.im.manager.UserInfoManager;
import com.moonsister.im.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/3.
 */
public class GetMoneyModelImpl implements GetMoneyModel {
    @Override
    public void loadbasicInfo(onLoadDateSingleListener listener) {
        Observable<GetMoneyBean> observable = ServerApi.getAppAPI().getGetmoney(UserInfoManager.getInstance().getAuthcode(),AppConstant.CHANNEL_ID);
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


    @Override
    public void paySubmit(String number, int money, onLoadDateSingleListener listener) {
        Observable<DefaultDataBean> observable = ServerApi.getAppAPI().getTiXianReason(number, money, UserInfoManager.getInstance().getAuthcode(), AppConstant.CHANNEL_ID);
        ObservableUtils.parser(observable, new ObservableUtils.Callback<DefaultDataBean>() {

            @Override
            public void onSuccess(DefaultDataBean bean) {
                listener.onSuccess(bean, DataType.DATA_ONE);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }
}
