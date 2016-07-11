package com.gouyin.im.login.model;

import com.gouyin.im.base.BaseIModel;

/**
 * Created by jb on 2016/7/11.
 */
public interface FindPasswordActivityModel extends BaseIModel {
    void getSecurityCode(String phoneNumber, onLoadDateSingleListener listener);

    void submit(String phone, String code, onLoadDateSingleListener listenter);
}
