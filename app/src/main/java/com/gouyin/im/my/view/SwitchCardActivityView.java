package com.gouyin.im.my.view;

import com.gouyin.im.base.BaseIView;
import com.gouyin.im.bean.CardInfoBean;

import java.util.List;

/**
 * Created by jb on 2016/7/4.
 */
public interface SwitchCardActivityView extends BaseIView {
    void setCardInfos(List<CardInfoBean.DataBean> datas);
}
