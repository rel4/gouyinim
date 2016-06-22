package com.gouyin.im.home.presenetr;


import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.GoodSelectBaen;
import com.gouyin.im.home.model.GoodSelectModelImpl;
import com.gouyin.im.home.view.GoodSelectView;

import com.gouyin.im.utils.LogUtils;

import java.util.List;

/**
 * Created by pc on 2016/6/3.
 */
public class GoodSelectPresenterImpl implements GoodSelectPresenter, BaseIModel.onLoadDateSingleListener<List<GoodSelectBaen.Data>> {
    private final GoodSelectModelImpl mGoodModel;
    private final GoodSelectView mGoodView;
    private int page = 1;

    public GoodSelectPresenterImpl(GoodSelectView goodSelectView) {
        this.mGoodView = goodSelectView;
        this.mGoodModel = new GoodSelectModelImpl();
    }

    @Override
    public void uploadGoodSelectDateList() {
        mGoodView.show();
        mGoodModel.loadGoodSelectDate("1", 1, this);
        page++;
    }

    @Override
    public void downloadGoodSelectDateList() {
        mGoodView.show();
        mGoodModel.loadGoodSelectDate("1", page, this);
    }

    @Override
    public void onSuccess(List<GoodSelectBaen.Data> list, BaseIModel.DataType type) {

        mGoodView.addGoodSelectDate(list);
        mGoodView.hide();
    }

    @Override
    public void onFailure(String msg, Throwable e) {
        mGoodView.hide();
        LogUtils.e(this, msg);
    }
}
