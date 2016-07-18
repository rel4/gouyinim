package com.moonsister.im.main.view;

import com.moonsister.im.base.BaseIView;
import com.moonsister.im.bean.UserInfoDetailBean;
import com.moonsister.im.bean.UserInfoListBean;

import java.util.List;

/**
 * Created by pc on 2016/6/6.
 */
public interface DynamicView extends BaseIView {
    void loadUserinfo(List<UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList> list);

    void switch2RewardActivity();

    void switch2SendMsgActivity();

    void switch2AppointmentActivity();

    void swicth2SendFlowersActivity();


    void setUserInfodetail(UserInfoDetailBean bean);

    void pageFinish();

    void deleteDynamic(String id);
}
