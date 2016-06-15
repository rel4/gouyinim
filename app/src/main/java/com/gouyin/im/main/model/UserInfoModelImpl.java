package com.gouyin.im.main.model;

import android.os.SystemClock;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.UserInfoBean;
import com.gouyin.im.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by pc on 2016/6/6.
 */
public class UserInfoModelImpl implements UserInfoModel {
    @Override
    public void loadUserInfoData(final BaseIModel.onLoadDateListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                final ArrayList<UserInfoBean> arraylist = new ArrayList<UserInfoBean>();
                for (int i = 0; i < 10; i++) {
                    UserInfoBean userInfoBean = new UserInfoBean();
                    userInfoBean.setName("item : " + i);
                    userInfoBean.setAction(i%2+1);
                    arraylist.add(userInfoBean);
                }
                UIUtils.onRunMainThred(new Runnable() {
                    @Override
                    public void run() {
                        listener.onSuccess(arraylist);
                    }
                });


            }
        }).start();
    }
}
