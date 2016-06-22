package com.gouyin.im.main.model;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.UserInfoListBean;

/**
 * Created by pc on 2016/6/6.
 */
public interface UserInfoModel {
    void loadUserInfoData(String userId, int page, BaseIModel.onLoadListDateListener<UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList> listener);

    void loadUserInfodetail(String uid, BaseIModel.onLoadDateSingleListener listener);
}
