package com.moonsister.im.my.view;

import com.moonsister.im.base.BaseIView;
import com.moonsister.im.bean.UserInfoDetailBean;
import com.moonsister.im.bean.UserInfoListBean;

import java.util.List;

/**
 * Created by jb on 2016/6/27.
 */
public interface MyFragmentView extends BaseIView {
    void swich2PersonRed();

    void swich2WithdRawDeposit();

    void swich2Appointment();

    void swich2PersonOrder();

    void swich2PersonSetting();

    void setListData(List<UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList> t);

    void setUserInfo(UserInfoDetailBean bean);

    void setUserBackground(String obj);

    void deleteDynamic(String id);
}
