package com.gouyin.im.my.persenter;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.my.view.AddCardActivityView;

/**
 * Created by jb on 2016/7/3.
 */
public interface AddCardActivityPersenter extends BaseIPresenter<AddCardActivityView> {
    void submitAccount(String cardNumber, String username, String cardType, String bankname);
}
