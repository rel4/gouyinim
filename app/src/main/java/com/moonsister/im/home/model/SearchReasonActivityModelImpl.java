package com.moonsister.im.home.model;

import com.moonsister.im.AppConstant;
import com.moonsister.im.ServerApi;
import com.moonsister.im.bean.SearchReasonBaen;
import com.moonsister.im.manager.UserInfoManager;
import com.moonsister.im.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/10.
 */
public class SearchReasonActivityModelImpl implements SearchReasonActivityModel {
    @Override
    public void loadBasicData(String key, int page, onLoadDateSingleListener<SearchReasonBaen> listener) {
        Observable<SearchReasonBaen> observable = ServerApi.getAppAPI().getSearchReason(key,page, UserInfoManager.getInstance().getAuthcode(), AppConstant.CHANNEL_ID);
        ObservableUtils.parser(observable, new ObservableUtils.Callback<SearchReasonBaen>() {
            @Override
            public void onSuccess(SearchReasonBaen bean) {
                listener.onSuccess(bean, DataType.DATA_ZERO);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }
}
