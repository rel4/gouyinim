package com.gouyin.im.home.presenetr;


import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.GoodSelectBaen;
import com.gouyin.im.home.model.GoodSelectModelImpl;
import com.gouyin.im.home.view.GoodSelectView;

import java.util.List;

/**
 * Created by pc on 2016/6/3.
 */
public class GoodSelectPresenterImpl implements GoodSelectPresenter, BaseIModel.onLoadDateSingleListener<List<GoodSelectBaen.Data>> {
    private final GoodSelectModelImpl mGoodModel;
    private final GoodSelectView mGoodView;
    private int page = 2;

    public GoodSelectPresenterImpl(GoodSelectView goodSelectView) {
        this.mGoodView = goodSelectView;
        this.mGoodModel = new GoodSelectModelImpl();
    }

    @Override
    public void uploadGoodSelectDateList(int pageType) {
        mGoodView.showLoading();
        mGoodModel.loadGoodSelectDate(pageType,"1", 1, this);
        page = 2;
    }

    @Override
    public void downloadGoodSelectDateList(int pageType) {
        mGoodView.showLoading();
        mGoodModel.loadGoodSelectDate(pageType,"1", page, this);
        page++;
    }

    @Override
    public void onSuccess(List<GoodSelectBaen.Data> list, BaseIModel.DataType type) {
        mGoodView.addGoodSelectDate(list);
        mGoodView.hideLoading();
    }

    @Override
    public void onFailure(String msg) {
        mGoodView.hideLoading();
        mGoodView.transfePageMsg(msg);
    }
}
