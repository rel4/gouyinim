package com.moonsister.im.login.model;

import com.moonsister.im.AppConstant;
import com.moonsister.im.ServerApi;
import com.moonsister.im.bean.BaseBean;
import com.moonsister.im.utils.MD5Util;
import com.moonsister.im.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/11.
 */
public class FindPasswordNextActivityModelImpl implements FindPasswordNextActivityModel {
    @Override
    public void submit(String newpwd, String code, onLoadDateSingleListener listenter) {
        Observable<BaseBean> observable = ServerApi.getAppAPI().getFindPasswordNext(MD5Util.string2MD5(newpwd), code, AppConstant.CHANNEL_ID);
        ObservableUtils.parser(observable, new ObservableUtils.Callback() {
            @Override
            public void onSuccess(BaseBean bean) {
                listenter.onSuccess(bean, DataType.DATA_ZERO);
            }

            @Override
            public void onFailure(String msg) {
                listenter.onFailure(msg);
            }
        });
    }
}
