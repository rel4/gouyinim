package com.gouyin.im.my.model;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.CardInfoBean;

/**
 * Created by jb on 2016/7/4.
 */
public interface SwitchCardActivityModel extends BaseIModel{
    void loadCardInfo( onLoadDateSingleListener<CardInfoBean> listener);

}
