package com.gouyin.im.my.model;

import com.gouyin.im.R;
import com.gouyin.im.ServerApi;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.UserInfoDetailBean;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/6/27.
 */
public class MyFragmentModelImpl implements MyFragmentModel {
    @Override
    public void loadPersonHeader(BaseIModel.onLoadDateSingleListener listener) {

        ServerApi.getAppAPI().loadPersonInfo(UserInfoManager.getInstance().getAuthcode())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                        listener.onSuccess(userInfoDetailBean, BaseIModel.DataType.DATA_ONE);
                        LogUtils.e(this, userInfoDetailBean.toString());
                    }
                });
    }

    @Override
    public void loadonRefreshData(int page, onLoadListDateListener listener) {

        ServerApi.getAppAPI().loadPersonDynamic(UserInfoManager.getInstance().getAuthcode(), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfoListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e.getMessage(), e);
                    }

                    @Override
                    public void onNext(UserInfoListBean userInfoListBean) {
                        if (userInfoListBean != null) {
                            String code = userInfoListBean.getCode();
                            if (StringUtis.equals("1", code)) {
                                listener.onSuccess(userInfoListBean.getData().getList(), DataType.DATA_ZERO);
                            } else if (StringUtis.equals("1000", code)) {
                                RxBus.getInstance().send(Events.EventEnum.LOGIN, null);
                            } else
                                listener.onFailure(UIUtils.getStringRes(R.string.request_failed), null);
                        }
                    }
                });


    }
}
