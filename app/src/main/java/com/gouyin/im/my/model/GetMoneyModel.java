package com.gouyin.im.my.model;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.GetMoneyBean;

/**
 * Created by jb on 2016/7/3.
 */
public interface GetMoneyModel extends BaseIModel {
    void loadbasicInfo(onLoadDateSingleListener<GetMoneyBean> listener);
}
