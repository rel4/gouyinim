package com.gouyin.im.main.presenter;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.UserInfoChangeBean;
import com.gouyin.im.main.model.UserinfoActivityModel;
import com.gouyin.im.main.model.UserinfoActivityModelImpl;
import com.gouyin.im.main.view.UserinfoActivityView;

/**
 * Created by jb on 2016/7/11.
 */
public class UserinfoActivityPresenterImpl implements UserinfoActivityPresenter, BaseIModel.onLoadDateSingleListener<UserInfoChangeBean> {
    private UserinfoActivityView view;
    private UserinfoActivityModel model;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(UserinfoActivityView userinfoActivityView) {
        this.view = userinfoActivityView;
        model = new UserinfoActivityModelImpl();
    }

    @Override
    public void loadBasicData(String uid) {
        view.showLoading();
        model.loadBasicData(uid,this);
    }

    @Override
    public void onSuccess(UserInfoChangeBean userInfoChangeBean, BaseIModel.DataType dataType) {
        view.setUserInfo(userInfoChangeBean);
        view.hideLoading();
    }

    @Override
    public void onFailure(String msg) {
        view.hideLoading();
        view.transfePageMsg(msg);
    }
}
