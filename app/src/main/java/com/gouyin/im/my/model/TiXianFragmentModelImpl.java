package com.gouyin.im.my.model;

import com.gouyin.im.ServerApi;
import com.gouyin.im.bean.TiXinrRecordBean;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/2.
 */
public class TiXianFragmentModelImpl implements TiXianFragmentModel {
    @Override
    public void loadTixin(onLoadDateSingleListener listener) {
        Observable<TiXinrRecordBean> observable = ServerApi.getAppAPI().getTixinRecord(UserInfoManager.getInstance().getAuthcode());
        ObservableUtils.parser(observable, new ObservableUtils.Callback<TiXinrRecordBean>() {
            @Override
            public void onSuccess(TiXinrRecordBean bean) {
                listener.onSuccess(bean, DataType.DATA_ZERO);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });


    }
}
