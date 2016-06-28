package com.gouyin.im.center.model;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.center.presenter.DefaultDynamicPresenterImpl;
import com.gouyin.im.center.widget.DynamicSendActivity;

import java.util.List;

/**
 * Created by jb on 2016/6/23.
 */
public interface DefaultDynamicModel extends BaseIModel {
    void sendDynamicPics(DynamicSendActivity.DynamicType dynamicType, String content, List<String> datas, String address, onLoadDateSingleListener defaultDynamicPresenter);
}
