package com.gouyin.im.center.presenter;

import com.gouyin.im.R;
import com.gouyin.im.aliyun.AliyunManager;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.center.model.DefaultDynamicModel;
import com.gouyin.im.center.model.DefaultDynamicModelImpl;
import com.gouyin.im.center.view.DefaultDynamicView;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

import java.util.List;

/**
 * Created by jb on 2016/6/23.
 */
public class DefaultDynamicPresenterImpl implements DefaultDynamicPresenter, BaseIModel.onLoadDateSingleListener {
    private DefaultDynamicView view;
    private DefaultDynamicModel model;
    private List<String> aliyunPaths;
    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(DefaultDynamicView defaultDynamicView) {
        this.view = defaultDynamicView;
        model = new DefaultDynamicModelImpl();

    }

    @Override
    public void sendDynamic(String content, List<String> datas, String address) {
        view.showLoading();
        model.sendDynamicPics(content,datas,null,address,this);

    }

    @Override
    public void onSuccess(Object o, BaseIModel.DataType dataType) {
        view.hideLoading();
        view.transfePageMsg(UIUtils.getStringRes(R.string.upload)+UIUtils.getStringRes(R.string.success));
        view.finishPage();
    }

    @Override
    public void onFailure(String msg, Throwable e) {
        view.hideLoading();
        view.transfePageMsg(UIUtils.getStringRes(R.string.request_failed));
    }
}
