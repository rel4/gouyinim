package com.gouyin.im.main.model;

import com.gouyin.im.R;
import com.gouyin.im.ServerApi;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.UserInfoDetailBean;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.bean.UserInfoListBeanData;
import com.gouyin.im.bean.UserInfoListBeanDataList;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.utils.UserInfoUtils;

import org.w3c.dom.ls.LSException;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pc on 2016/6/6.
 */
public class UserInfoModelImpl implements UserInfoModel {


    @Override
    public void loadUserInfoData(String userId, int page, BaseIModel.onLoadListDateListener<UserInfoListBeanDataList> listener) {
        String authcode = UserInfoUtils.getAuthcode();
        ServerApi.getAppAPI().getPersonDynamincList(userId,authcode,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfoListBean>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e.getMessage(),e);
                    }

                    @Override
                    public void onNext(UserInfoListBean userInfoListBean) {
                            if (userInfoListBean!=null){
                                UserInfoListBeanData data = userInfoListBean.getData();
                                if (data!=null){
                                    List<UserInfoListBeanDataList> list = data.getList();
                                    listener.onSuccess(list,0);
                                }
                            }
                        listener.onFailure(UIUtils.getStringRes(R.string.request_failed),null);
                    }
                });

    }

    @Override
    public void loadUserInfodetail(String uid, BaseIModel.onLoadDateSingleListener listener) {
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
