package com.gouyin.im.main.presenter;

import com.gouyin.im.base.onLoadDateListener;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.main.model.DynamincDatailsModelImpl;
import com.gouyin.im.main.view.DynamicDatailsView;

import java.util.List;

/**
 * Created by pc on 2016/6/8.
 */
public class DynamincDatailsPresenterImpl implements DynamincDatailsPresenter, onLoadDateListener<List<BaseBean>> {
    private DynamicDatailsView view;
    private DynamincDatailsModelImpl dynamincDatailsModel;

    public DynamincDatailsPresenterImpl(DynamicDatailsView view) {
        this.view = view;
        dynamincDatailsModel = new DynamincDatailsModelImpl();
    }

    @Override
    public void loadData() {
        view.show();
        dynamincDatailsModel.loadData(this);

    }

    @Override
    public void onSuccess(List<BaseBean> beans) {
        view.loadData(beans);
        view.hide();
    }

    @Override
    public void onFailure(String msg, Exception e) {

    }
}
