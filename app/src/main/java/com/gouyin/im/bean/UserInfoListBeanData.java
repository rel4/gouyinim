package com.gouyin.im.bean;

import java.util.List;

public class UserInfoListBeanData  extends BaseDataBean{
    private List<UserInfoListBeanDataList> list;

    public List<UserInfoListBeanDataList> getList() {
        return this.list;
    }

    public void setList(List<UserInfoListBeanDataList> list) {
        this.list = list;
    }
}
