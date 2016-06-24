package com.gouyin.im.main.model;

import com.gouyin.im.base.BaseIModel;

/**
 * Created by jb on 2016/6/24.
 */
public interface PlayUserAcitivityModel extends BaseIModel {
    void aliPay(String playType,String uid, String money);
}

