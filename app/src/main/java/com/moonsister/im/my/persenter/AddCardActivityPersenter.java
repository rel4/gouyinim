package com.moonsister.im.my.persenter;

import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.my.view.AddCardActivityView;

/**
 * Created by jb on 2016/7/3.
 */
public interface AddCardActivityPersenter extends BaseIPresenter<AddCardActivityView> {
    void submitAccount(String cardNumber, String username, String cardType, String bankname);
}
