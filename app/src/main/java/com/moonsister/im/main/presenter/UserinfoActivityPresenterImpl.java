package com.moonsister.im.main.presenter;

import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.bean.UserInfoChangeBean;
import com.moonsister.im.main.model.UserinfoActivityModel;
import com.moonsister.im.main.model.UserinfoActivityModelImpl;
import com.moonsister.im.main.view.UserinfoActivityView;

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
