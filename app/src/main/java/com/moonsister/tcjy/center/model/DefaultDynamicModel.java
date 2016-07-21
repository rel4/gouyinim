package com.moonsister.tcjy.center.model;

import com.moonsister.tcjy.base.BaseIModel;
import com.moonsister.tcjy.center.widget.DynamicSendActivity;

import java.util.List;

/**
 * Created by jb on 2016/6/23.
 */
public interface DefaultDynamicModel extends BaseIModel {
    void sendDynamicPics(DynamicSendActivity.DynamicType dynamicType, String content, List<String> datas, String address, onLoadDateSingleListener defaultDynamicPresenter);
}
