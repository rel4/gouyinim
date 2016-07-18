package com.moonsister.im.my.model;

import com.moonsister.im.AppConstant;
import com.moonsister.im.ServerApi;
import com.moonsister.im.bean.TiXinrRecordBean;
import com.moonsister.im.manager.UserInfoManager;
import com.moonsister.im.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/2.
 */
public class TiXianFragmentModelImpl implements TiXianFragmentModel {
    @Override
    public void loadTixin(onLoadDateSingleListener listener) {
        Observable<TiXinrRecordBean> observable = ServerApi.getAppAPI().getTixinRecord(UserInfoManager.getInstance().getAuthcode(), AppConstant.CHANNEL_ID);
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
