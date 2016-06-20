package com.gouyin.im.main.model;

import android.os.SystemClock;

import com.gouyin.im.ServerApi;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.UserInfoBean;
import com.gouyin.im.bean.UserInfoDetailBean;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.utils.UserInfoUtils;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pc on 2016/6/6.
 */
public class UserInfoModelImpl implements UserInfoModel {
    @Override
    public void loadUserInfoData(final BaseIModel.onLoadListDateListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                final ArrayList<UserInfoBean> arraylist = new ArrayList<UserInfoBean>();
                for (int i = 0; i < 10; i++) {
                    UserInfoBean userInfoBean = new UserInfoBean();
                    userInfoBean.setName("item : " + i);
                    userInfoBean.setAction(i % 2 + 1);
                    arraylist.add(userInfoBean);
                }
                UIUtils.onRunMainThred(new Runnable() {
                    @Override
                    public void run() {
                        listener.onSuccess(arraylist, 0);
                    }
                });


            }
        }).start();
    }


    @Override
    public void loadUserInfodetail(String uid, BaseIModel.onLoadDateSingleListener listener) {
        LogUtils.e(this, "loadUserInfodetail-----------");
        String authcode = UserInfoUtils.getAuthcode();
        ServerApi.getAppAPI().getUserInfoDetail(uid, authcode).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<UserInfoDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e.getMessage(), e);
                    }

                    @Override
                    public void onNext(UserInfoDetailBean userInfoDetailBean) {
                        listener.onSuccess(userInfoDetailBean, 1);
                        LogUtils.e(this, userInfoDetailBean.toString());
                    }
                });
    }


}
