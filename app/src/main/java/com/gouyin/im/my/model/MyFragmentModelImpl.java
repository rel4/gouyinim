package com.gouyin.im.my.model;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.gouyin.im.R;
import com.gouyin.im.ServerApi;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.bean.UserInfoDetailBean;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.manager.aliyun.AliyunManager;
import com.gouyin.im.utils.FilePathUtlis;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.ObservableUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

import java.io.File;

import rx.Observable;
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
                        listener.onFailure(e.getMessage());
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
                        listener.onFailure(e.getMessage());
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
                                listener.onFailure(UIUtils.getStringRes(R.string.request_failed));
                        }
                    }
                });


    }

    @Override
    public void uploadBackground(String path, onLoadDateSingleListener listener) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String s = AliyunManager.getInstance().upLoadFile(path, FilePathUtlis.FileType.JPG);
                    File file = new File(path);
                    if (file.exists())
                        file.delete();
                    subscriber.onNext(s);
                } catch (ClientException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                } catch (ServiceException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(UIUtils.getStringRes(R.string.request_failed));
                    }

                    @Override
                    public void onNext(String s) {
                        Observable<DefaultDataBean> observable = ServerApi.getAppAPI().getUploadBackground(s, UserInfoManager.getInstance().getAuthcode());
                        ObservableUtils.parser(observable, new ObservableUtils.Callback<DefaultDataBean>() {
                            @Override
                            public void onSuccess(DefaultDataBean bean) {
                                bean.setObj(s);
                                listener.onSuccess(bean, DataType.DATA_TWO);

                            }

                            @Override
                            public void onFailure(String msg) {
                                listener.onFailure(msg);
                            }
                        });
                    }
                });
    }
}
