package com.gouyin.im.main.presenter;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.main.view.PlayUserAcitivityView;

/**
 * Created by jb on 2016/6/24.
 */
public interface RedpacketAcitivityPresenter extends BaseIPresenter<PlayUserAcitivityView> {

    void swicthAction(int id);

    void aliPay(String uid, String trim);
}

