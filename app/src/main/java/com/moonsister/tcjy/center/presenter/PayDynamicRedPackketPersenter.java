package com.moonsister.tcjy.center.presenter;


import com.moonsister.tcjy.base.BaseIPresenter;
import com.moonsister.tcjy.center.view.PayDynamicRedPackketView;

/**
 * Created by jb on 2016/6/29.
 */
public interface PayDynamicRedPackketPersenter  extends BaseIPresenter <PayDynamicRedPackketView>{
    void alipay(String id);

    void weixinPay(String id);

    void getPics(String id);
}
