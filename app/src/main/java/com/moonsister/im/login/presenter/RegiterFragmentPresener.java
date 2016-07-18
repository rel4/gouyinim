package com.moonsister.im.login.presenter;

import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.login.view.RegiterFragmentView;

/**
 * Created by pc on 2016/6/14.
 */
public interface RegiterFragmentPresener extends BaseIPresenter<RegiterFragmentView>{
    void getSecurityCode(String phoneNumber);

    void submitRegiter(String phoneNumber,String code);
}
