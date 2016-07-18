package com.moonsister.im.center.presenter;

import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.center.view.DefaultDynamicView;
import com.moonsister.im.center.widget.DynamicSendActivity;

import java.util.List;

/**
 * Created by jb on 2016/6/23.
 */
public interface DefaultDynamicPresenter extends BaseIPresenter<DefaultDynamicView> {
    void sendDynamic(DynamicSendActivity.DynamicType dynamicType, String content, List<String> datas, String address);
}
