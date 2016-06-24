package com.gouyin.im.main.presenter;

import com.gouyin.im.R;
import com.gouyin.im.main.model.PlayUserAcitivityModel;
import com.gouyin.im.main.model.PlayUserAcitivityModelImpl;
import com.gouyin.im.main.view.PlayUserAcitivityView;

/**
 * Created by jb on 2016/6/24.
 */
public class RedpacketAcitivityPresenterImpl implements RedpacketAcitivityPresenter {
    private PlayUserAcitivityView view;
    private PlayUserAcitivityModel model;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(PlayUserAcitivityView baseIView) {
        this.view = baseIView;
         model = new PlayUserAcitivityModelImpl();

    }

    @Override
    public void swicthAction(int id) {
        switch (id) {
            case R.id.action_back:
                break;
            case R.id.tv_weixin_play:
                break;
            case R.id.tv_aliplay_play:
                view.swicthAliPlay();
                break;
        }
    }

    @Override
    public void aliPay(String uid, String money) {
        view.showLoading();
        model.aliPay("alipay",uid,money);
    }

}
