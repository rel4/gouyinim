package com.gouyin.im.my.persenter;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.CardInfoBean;
import com.gouyin.im.my.model.SwitchCardActivityModel;
import com.gouyin.im.my.model.SwitchCardActivityModelImpl;
import com.gouyin.im.my.view.SwitchCardActivityView;
import com.gouyin.im.utils.UIUtils;

import java.util.List;

/**
 * Created by jb on 2016/7/4.
 */
public class SwitchCardActivityPresenterImpl implements SwitchCardActivityPresenter, BaseIModel.onLoadDateSingleListener<CardInfoBean> {
    private SwitchCardActivityView view;
    private SwitchCardActivityModel model;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(SwitchCardActivityView switchCardActivityView) {
        this.view = switchCardActivityView;
        model = new SwitchCardActivityModelImpl();
    }

    @Override
    public void loadCardInfo() {
        view.showLoading();
        model.loadCardInfo(this);
    }

    @Override
    public void onSuccess(CardInfoBean cardInfoBean, BaseIModel.DataType dataType) {
        if (cardInfoBean != null) {
            List<CardInfoBean.DataBean> datas = cardInfoBean.getData();
            if (datas != null) {
                view.setCardInfos(datas);
            } else
                view.transfePageMsg(cardInfoBean.getMsg());
        } else {
            view.transfePageMsg(UIUtils.getStringRes(R.string.request_failed));
        }
        view.hideLoading();

    }

    @Override
    public void onFailure(String msg) {
        view.transfePageMsg(msg);
        view.hideLoading();
    }
}
