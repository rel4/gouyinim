package com.gouyin.im.im.model;

import com.gouyin.im.ServerApi;
import com.gouyin.im.bean.FrientBaen;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.ObservableUtils;

import java.util.zip.ZipEntry;

import rx.Observable;

/**
 * Created by jb on 2016/7/8.
 */
public class FrientFragmentModelImpl implements FrientFragmentModel {
    @Override
    public void loadBasicData(int pageType, int page, onLoadDateSingleListener listener) {
        Observable<FrientBaen> observable = null;
        if (pageType == 1)
            observable = ServerApi.getAppAPI().getWacthList(page, UserInfoManager.getInstance().getAuthcode());
        else
            observable = ServerApi.getAppAPI().getFenList(page, UserInfoManager.getInstance().getAuthcode());
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
