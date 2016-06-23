package com.gouyin.im.center.presenter;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.center.view.DefaultDynamicView;

import java.util.List;

/**
 * Created by jb on 2016/6/23.
 */
public interface DefaultDynamicPresenter extends BaseIPresenter<DefaultDynamicView> {
    void sendDynamic(String content, List<String> datas, String address);
}
