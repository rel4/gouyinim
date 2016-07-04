package com.gouyin.im.my.view;

import com.gouyin.im.base.BaseIView;
import com.gouyin.im.bean.GetMoneyBean;

/**
 * Created by jb on 2016/7/3.
 */
public interface GetMoneyView extends BaseIView {
    void setBasicInfo(GetMoneyBean getMoneyBean);

    void pageFinish();
}
