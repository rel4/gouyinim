package com.moonsister.im.center.presenter;

import com.moonsister.im.R;
import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.center.model.DefaultDynamicModel;
import com.moonsister.im.center.model.DefaultDynamicModelImpl;
import com.moonsister.im.center.view.DefaultDynamicView;
import com.moonsister.im.center.widget.DynamicSendActivity;
import com.moonsister.im.utils.UIUtils;

import java.util.List;

/**
 * Created by jb on 2016/6/23.
 */
public class DefaultDynamicPresenterImpl implements DefaultDynamicPresenter, BaseIModel.onLoadDateSingleListener {
    private DefaultDynamicView view;
    private DefaultDynamicModel model;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(DefaultDynamicView defaultDynamicView) {
        this.view = defaultDynamicView;
        model = new DefaultDynamicModelImpl();

    }

    @Override
    public void sendDynamic(DynamicSendActivity.DynamicType dynamicType, String content, List<String> datas, String address) {
        view.showLoading();
        model.sendDynamicPics(dynamicType, content, datas, address, this);

    }

    @Override
    public void onSuccess(Object o, BaseIModel.DataType dataType) {
        view.hideLoading();
        view.transfePageMsg(UIUtils.getStringRes(R.string.upload) + UIUtils.getStringRes(R.string.success));
        view.finishPage();
    }

    @Override
    public void onFailure(String msg) {
        view.hideLoading();
        view.transfePageMsg(UIUtils.getStringRes(R.string.request_failed));
    }
}