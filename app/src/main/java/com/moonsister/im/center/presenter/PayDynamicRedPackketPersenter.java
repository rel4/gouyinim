package com.moonsister.im.center.presenter;


import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.center.view.PayDynamicRedPackketView;

/**
 * Created by jb on 2016/6/29.
 */
public interface PayDynamicRedPackketPersenter  extends BaseIPresenter <PayDynamicRedPackketView>{
    void alipay(String id);

    void weixinPay(String id);

    void getPics(String id);
}
