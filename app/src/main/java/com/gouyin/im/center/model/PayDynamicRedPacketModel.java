package com.gouyin.im.center.model;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.PayRedPacketPicsBean;

/**
 * Created by jb on 2016/6/29.
 */
public interface PayDynamicRedPacketModel extends BaseIModel {
    void pay(String id, onLoadDateSingleListener<PayRedPacketPicsBean> listener);
}