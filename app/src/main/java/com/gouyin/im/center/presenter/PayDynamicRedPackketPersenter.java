package com.gouyin.im.center.presenter;


import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.center.view.PayDynamicRedPackketView;

/**
 * Created by jb on 2016/6/29.
 */
public interface PayDynamicRedPackketPersenter  extends BaseIPresenter <PayDynamicRedPackketView>{
    void alipay(String id);

    void weixinPay(String id);

    void getPics(String id);
}
