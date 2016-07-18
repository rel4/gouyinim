package com.moonsister.im.main.presenter;

import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.main.view.PlayUserAcitivityView;

/**
 * Created by jb on 2016/6/24.
 */
public interface RedpacketAcitivityPresenter extends BaseIPresenter<PlayUserAcitivityView> {

    void swicthAction(int id);

    void aliPay(int type, String uid, String trim);

    void weixinPay(int type, String uid, String trim);
}

