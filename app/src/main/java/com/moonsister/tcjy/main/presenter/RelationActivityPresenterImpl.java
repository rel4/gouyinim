package com.moonsister.tcjy.main.presenter;

import com.moonsister.tcjy.base.BaseIModel;
import com.moonsister.tcjy.bean.FrientBaen;
import com.moonsister.tcjy.main.model.RelationActivityModel;
import com.moonsister.tcjy.main.model.RelationActivityModelImpl;
import com.moonsister.tcjy.main.view.RelationActivityView;

/**
 * Created by jb on 2016/7/22.
 */
public class RelationActivityPresenterImpl implements RelationActivityPresenter, BaseIModel.onLoadDateSingleListener<FrientBaen> {
    private RelationActivityView view;
    private RelationActivityModel model;
    private int page = 1;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(RelationActivityView relationActivityView) {
        this.view = relationActivityView;
        model = new RelationActivityModelImpl();
    }

    @Override
    public void loadData(int type, String uid) {
        view.showLoading();
        model.loadData(type, page, uid, this);
    }

    @Override
    public void onSuccess(FrientBaen frientBaen, BaseIModel.DataType dataType) {
        view.setFrientData(frientBaen);
        view.hideLoading();
    }

    @Override
    public void onFailure(String msg) {

    }
}
