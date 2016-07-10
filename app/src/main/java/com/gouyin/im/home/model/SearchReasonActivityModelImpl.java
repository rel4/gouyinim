package com.gouyin.im.home.model;

import com.gouyin.im.ServerApi;
import com.gouyin.im.bean.SearchReasonBaen;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.ObservableUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/10.
 */
public class SearchReasonActivityModelImpl implements SearchReasonActivityModel {
    @Override
    public void loadBasicData(String key, int page, onLoadDateSingleListener<SearchReasonBaen> listener) {
        Observable<SearchReasonBaen> observable = ServerApi.getAppAPI().getSearchReason(key,page, UserInfoManager.getInstance().getAuthcode());
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
