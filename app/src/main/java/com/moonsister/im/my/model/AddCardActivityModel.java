package com.moonsister.im.my.model;

import com.moonsister.im.base.BaseIModel;

/**
 * Created by jb on 2016/7/3.
 */
public interface AddCardActivityModel extends BaseIModel {
    void submitAccount(String cardNumber, String username, String cardType, String bankname, onLoadDateSingleListener listener);
}
