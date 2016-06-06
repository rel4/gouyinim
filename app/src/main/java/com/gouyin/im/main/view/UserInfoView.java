package com.gouyin.im.main.view;

import com.gouyin.im.bean.UserInfoBean;

import java.util.List;

/**
 * Created by pc on 2016/6/6.
 */
public interface UserInfoView {
    void loadUserinfo(List<UserInfoBean> list);

    void switch2RewardActivity();

    void switch2SendMsgActivity();

    void switch2AppointmentActivity();

    void swicth2SendFlowersActivity();

    void show();

    void hide();
}
