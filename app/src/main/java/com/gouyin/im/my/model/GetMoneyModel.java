package com.gouyin.im.my.model;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.bean.GetMoneyBean;
import com.gouyin.im.my.persenter.GetMoneyPersenterImpl;

/**
 * Created by jb on 2016/7/3.
 */
public interface GetMoneyModel extends BaseIModel {
    void loadbasicInfo(onLoadDateSingleListener listener);

    void paySubmit(String number, int money, onLoadDateSingleListener listener);
}
