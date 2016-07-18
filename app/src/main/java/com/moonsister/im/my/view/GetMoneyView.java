package com.moonsister.im.my.view;

import com.moonsister.im.base.BaseIView;
import com.moonsister.im.bean.GetMoneyBean;

/**
 * Created by jb on 2016/7/3.
 */
public interface GetMoneyView extends BaseIView {
    void setBasicInfo(GetMoneyBean getMoneyBean);

    void pageFinish();
}
