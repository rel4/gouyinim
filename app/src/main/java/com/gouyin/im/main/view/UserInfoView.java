package com.gouyin.im.main.view;

import com.gouyin.im.base.BaseIView;
import com.gouyin.im.bean.UserInfoDetailBean;
import com.gouyin.im.bean.UserInfoListBean;

import java.util.List;

/**
 * Created by pc on 2016/6/6.
 */
public interface UserInfoView extends BaseIView {
    void loadUserinfo(List<UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList> list);

    void switch2RewardActivity();

    void switch2SendMsgActivity();

    void switch2AppointmentActivity();

    void swicth2SendFlowersActivity();


    void setUserInfodetail(UserInfoDetailBean bean);
}
