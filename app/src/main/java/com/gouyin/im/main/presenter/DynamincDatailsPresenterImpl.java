package com.gouyin.im.main.presenter;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.BaseDataBean;
import com.gouyin.im.main.model.DynamincDatailsModelImpl;
import com.gouyin.im.main.view.DynamicDatailsView;

import java.util.List;

/**
 * Created by pc on 2016/6/8.
 */
public class DynamincDatailsPresenterImpl implements DynamincDatailsPresenter, BaseIModel.onLoadDateSingleListener<List<BaseDataBean>> {
    private DynamicDatailsView view;
    private DynamincDatailsModelImpl dynamincDatailsModel;

    public DynamincDatailsPresenterImpl(DynamicDatailsView view) {
        this.view = view;
        dynamincDatailsModel = new DynamincDatailsModelImpl();
    }

    @Override
    public void loadData(String fileName) {
        view.show();
        dynamincDatailsModel.loadData(fileName,this);

    }

    @Override
    public void onSuccess(List<BaseDataBean> beans, int type) {
        view.loadData(beans);
        view.hide();
    }

    @Override
    public void onFailure(String msg, Throwable e) {

    }
}
