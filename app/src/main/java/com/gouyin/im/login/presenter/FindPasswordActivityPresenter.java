package com.gouyin.im.login.presenter;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.main.view.FindPasswordActivityView;

/**
 * Created by jb on 2016/7/11.
 */
public interface FindPasswordActivityPresenter extends BaseIPresenter<FindPasswordActivityView> {
    void getSecurityCode(String phoneNumber);

    void submitRegiter(String phone, String code);
}
