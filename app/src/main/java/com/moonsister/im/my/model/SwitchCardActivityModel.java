package com.moonsister.im.my.model;

import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.bean.CardInfoBean;

/**
 * Created by jb on 2016/7/4.
 */
public interface SwitchCardActivityModel extends BaseIModel{
    void loadCardInfo( onLoadDateSingleListener<CardInfoBean> listener);

}
