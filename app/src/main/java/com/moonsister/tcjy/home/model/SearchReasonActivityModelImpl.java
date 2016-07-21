package com.moonsister.tcjy.home.model;

import com.moonsister.tcjy.AppConstant;
import com.moonsister.tcjy.ServerApi;
import com.moonsister.tcjy.bean.SearchReasonBaen;
import com.moonsister.tcjy.manager.UserInfoManager;
import com.moonsister.tcjy.utils.ObservableUtils;

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
