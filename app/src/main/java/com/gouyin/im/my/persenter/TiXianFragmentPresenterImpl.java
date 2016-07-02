package com.gouyin.im.my.persenter;

import com.gouyin.im.AppConstant;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.TiXinrRecordBean;
import com.gouyin.im.my.model.TiXianFragmentModel;
import com.gouyin.im.my.model.TiXianFragmentModelImpl;
import com.gouyin.im.my.view.TiXianFragmentView;
import com.gouyin.im.utils.StringUtis;

/**
 * Created by jb on 2016/7/2.
 */
public class TiXianFragmentPresenterImpl implements TiXianFragmentPresenter, BaseIModel.onLoadDateSingleListener<TiXinrRecordBean> {
    private TiXianFragmentView view;
    private TiXianFragmentModel model;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(TiXianFragmentView tiXianFragmentView) {
        this.view = tiXianFragmentView;
        model = new TiXianFragmentModelImpl();
    }

    @Override
    public void loadTixin() {
        view.showLoading();
        model.loadTixin(this);
    }

    @Override
    public void onSuccess(TiXinrRecordBean bean, BaseIModel.DataType dataType) {
        if (StringUtis.equals(bean.getCode(), AppConstant.code_request_success)) {
            view.setLoadData(bean);
        } else {
            view.transfePageMsg(bean.getMsg());
        }

        view.hideLoading();

    }

    @Override
    public void onFailure(String msg) {
        view.hideLoading();
        view.transfePageMsg(msg);
    }
}
