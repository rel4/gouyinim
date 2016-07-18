package com.moonsister.im.my.model;

import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.bean.DefaultDataBean;

/**
 * Created by jb on 2016/7/2.
 */
public interface WithdRawDepositModel extends BaseIModel {
    void loadEnableMoney(onLoadDateSingleListener<DefaultDataBean> listener);
}
