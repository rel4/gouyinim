package com.gouyin.im.login.presenter;

import com.gouyin.im.base.BaseIPresenter;

/**
 * Created by pc on 2016/6/14.
 */
public interface RegiterFragmentPresener<T> extends BaseIPresenter<T>{
    void getSecurityCode(String phoneNumber);

    void submitRegiter(String phoneNumber,String code);
}
