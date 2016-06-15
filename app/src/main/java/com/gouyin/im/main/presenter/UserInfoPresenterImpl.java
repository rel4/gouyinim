package com.gouyin.im.main.presenter;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.UserInfoBean;
import com.gouyin.im.main.model.UserInfoModel;
import com.gouyin.im.main.model.UserInfoModelImpl;
import com.gouyin.im.main.view.UserInfoView;

import java.util.List;

/**
 * Created by pc on 2016/6/6.
 */
public class UserInfoPresenterImpl implements UserInfoPresenter, BaseIModel.onLoadDateListener<List<UserInfoBean>> {
    private final UserInfoView mUserInfoView;
    private UserInfoModel mUserInfoModel;

    public UserInfoPresenterImpl(UserInfoView userInfoView) {
        this.mUserInfoView = userInfoView;
        mUserInfoModel = new UserInfoModelImpl();
    }

    @Override
    public void loadUserInfoData() {
        mUserInfoView.show();
        mUserInfoModel.loadUserInfoData(this);
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.tv_appointment:
                mUserInfoView.switch2AppointmentActivity();
                break;
            case R.id.tv_send_flowers:
                mUserInfoView.swicth2SendFlowersActivity();
                break;
            case R.id.tv_send_msg:
                mUserInfoView.switch2SendMsgActivity();
                break;
            case R.id.tv_reward:
                mUserInfoView.switch2RewardActivity();
                break;
        }
    }


    @Override
    public void onSuccess(List<UserInfoBean> users) {

        mUserInfoView.loadUserinfo(users);
        mUserInfoView.hide();
    }

    @Override
    public void onFailure(String msg, Throwable e) {

    }
}
