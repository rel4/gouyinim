package com.gouyin.im.login.presenter;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.login.view.RegiterFragmentView;

/**
 * Created by pc on 2016/6/14.
 */
public interface RegiterFragmentPresener extends BaseIPresenter<RegiterFragmentView>{
    void getSecurityCode(String phoneNumber);

    void submitRegiter(String phoneNumber,String code);
}
