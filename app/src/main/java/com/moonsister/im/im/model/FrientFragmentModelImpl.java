package com.moonsister.im.im.model;

import com.moonsister.im.AppConstant;
import com.moonsister.im.ServerApi;
import com.moonsister.im.bean.FrientBaen;
import com.moonsister.im.manager.UserInfoManager;
import com.moonsister.im.utils.ObservableUtils;

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
