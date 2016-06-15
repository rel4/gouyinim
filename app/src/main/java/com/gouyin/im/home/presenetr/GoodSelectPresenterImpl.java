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
public class GoodSelectPresenterImpl implements GoodSelectPresenter ,BaseIModel.onLoadDateListener<List<GoodSelectBaen>> {
    private final GoodSelectModelImpl mGoodModel;
    private final GoodSelectView mGoodView;

    public GoodSelectPresenterImpl(GoodSelectView goodSelectView){
        this.mGoodView =goodSelectView;
        this.mGoodModel = new GoodSelectModelImpl();
    }
    @Override
    public void loadGoodSelectDateList() {
        mGoodView.show();
        mGoodModel.loadGoodSelectDate(this);
    }

    @Override
    public void onSuccess(List<GoodSelectBaen> list) {

        mGoodView.addGoodSelectDate(list);
        mGoodView.hide();
    }

    @Override
    public void onFailure(String msg, Throwable e) {
        mGoodView.hide();
        LogUtils.e(this,msg);
    }
}
