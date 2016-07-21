package com.moonsister.tcjy.im.model;

import com.moonsister.tcjy.AppConstant;
import com.moonsister.tcjy.ServerApi;
import com.moonsister.tcjy.bean.FrientBaen;
import com.moonsister.tcjy.manager.UserInfoManager;
import com.moonsister.tcjy.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/8.
 */
public class FrientFragmentModelImpl implements FrientFragmentModel {
    @Override
    public void loadBasicData(int pageType, int page, onLoadDateSingleListener listener) {
        Observable<FrientBaen> observable = null;
        if (pageType == 1)
            observable = ServerApi.getAppAPI().getWacthList(page, UserInfoManager.getInstance().getAuthcode(), AppConstant.CHANNEL_ID);
        else
            observable = ServerApi.getAppAPI().getFenList(page, UserInfoManager.getInstance().getAuthcode(),AppConstant.CHANNEL_ID);
        ObservableUtils.parser(observable, new ObservableUtils.Callback<FrientBaen>() {
            @Override
            public void onSuccess(FrientBaen bean) {
                listener.onSuccess(bean, DataType.DATA_ZERO);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }
}
