package com.gouyin.im.login.model;

import com.gouyin.im.ServerApi;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.utils.MD5Util;
import com.gouyin.im.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/11.
 */
public class FindPasswordNextActivityModelImpl implements FindPasswordNextActivityModel {
    @Override
    public void submit(String newpwd, String code, onLoadDateSingleListener listenter) {
        Observable<BaseBean> observable = ServerApi.getAppAPI().getFindPasswordNext(MD5Util.string2MD5(newpwd), code);
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
