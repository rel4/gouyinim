package com.moonsister.tcjy.center.presenter;

import com.moonsister.tcjy.base.BaseIPresenter;
import com.moonsister.tcjy.center.view.DefaultDynamicView;
import com.moonsister.tcjy.center.widget.DynamicSendActivity;

import java.util.List;

/**
 * Created by jb on 2016/6/23.
 */
public interface DefaultDynamicPresenter extends BaseIPresenter<DefaultDynamicView> {
    void sendDynamic(DynamicSendActivity.DynamicType dynamicType, String content, List<String> datas, String address);
}