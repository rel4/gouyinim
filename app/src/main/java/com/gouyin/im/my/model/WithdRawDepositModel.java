package com.gouyin.im.my.model;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.DefaultDataBean;

/**
 * Created by jb on 2016/7/2.
 */
public interface WithdRawDepositModel extends BaseIModel {
    void loadEnableMoney(onLoadDateSingleListener<DefaultDataBean> listener);
}
