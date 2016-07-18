package com.moonsister.im.my.view;

import com.moonsister.im.base.BaseIView;
import com.moonsister.im.bean.CardInfoBean;

import java.util.List;

/**
 * Created by jb on 2016/7/4.
 */
public interface SwitchCardActivityView extends BaseIView {
    void setCardInfos(List<CardInfoBean.DataBean> datas);
}
