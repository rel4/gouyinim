package com.moonsister.im.login.model;

import com.moonsister.im.base.BaseIModel;

/**
 * Created by jb on 2016/7/11.
 */
public interface FindPasswordActivityModel extends BaseIModel {
    void getSecurityCode(String phoneNumber, onLoadDateSingleListener listener);

    void submit(String phone, String code, onLoadDateSingleListener listenter);
}
