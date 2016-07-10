package com.gouyin.im.home.presenetr;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.SearchReasonBaen;
import com.gouyin.im.home.model.SearchReasonActivityModel;
import com.gouyin.im.home.model.SearchReasonActivityModelImpl;
import com.gouyin.im.home.view.SearchReasonActivityView;

/**
 * Created by jb on 2016/7/10.
 */
public class SearchReasonActivityPresenterImpl implements SearchReasonActivityPresenter, BaseIModel.onLoadDateSingleListener<SearchReasonBaen> {
    private SearchReasonActivityView view;
    private SearchReasonActivityModel model;
    private int page = 1;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(SearchReasonActivityView searchReasonActivityView) {
        this.view = searchReasonActivityView;
        model = new SearchReasonActivityModelImpl();
    }

    @Override
    public void loadBasicData(String key) {
        view.showLoading();
        model.loadBasicData(key, page, this);
    }

    @Override
    public void onSuccess(SearchReasonBaen searchReasonBaen, BaseIModel.DataType dataType) {
        view.setReasonData(searchReasonBaen);
        view.hideLoading();

    }

    @Override
    public void onFailure(String msg) {
        view.hideLoading();
        view.transfePageMsg(msg);
    }
}
