package com.gouyin.im.login.model;

import com.gouyin.im.ServerApi;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.RegiterBean;
import com.gouyin.im.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/11.
 */
public class FindPasswordActivityModelImpl implements FindPasswordActivityModel {
    @Override
    public void getSecurityCode(String phoneNumber, onLoadDateSingleListener listener) {
        Observable<BaseBean> observable = ServerApi.getAppAPI().getSecurityCode(phoneNumber);
        ObservableUtils.parser(observable, new ObservableUtils.Callback() {
            @Override
            public void onSuccess(BaseBean bean) {
                listener.onSuccess(bean, DataType.DATA_ZERO);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }

    @Override
    public void submit(String phone, String code, onLoadDateSingleListener listenter) {
        Observable<RegiterBean> observable = ServerApi.getAppAPI().getFindPasswordSecurity(phone, code);
        ObservableUtils.parser(observable, new ObservableUtils.Callback<RegiterBean>() {
            @Override
            public void onSuccess(RegiterBean bean) {
                listenter.onSuccess(bean, DataType.DATA_ONE);
            }

            @Override
            public void onFailure(String msg) {
                listenter.onFailure(msg);
            }
        });
    }
}
