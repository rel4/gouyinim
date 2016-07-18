package com.moonsister.im.my.model;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.moonsister.im.AppConstant;
import com.moonsister.im.R;
import com.moonsister.im.ServerApi;
import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.bean.DefaultDataBean;
import com.moonsister.im.bean.UserInfoDetailBean;
import com.moonsister.im.bean.UserInfoListBean;
import com.moonsister.im.manager.UserInfoManager;
import com.moonsister.im.manager.aliyun.AliyunManager;
import com.moonsister.im.utils.FilePathUtlis;
import com.moonsister.im.utils.ObservableUtils;
import com.moonsister.im.utils.UIUtils;

import java.io.File;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/6/27.
 */
public class MyFragmentModelImpl implements MyFragmentModel {
    @Override
    public void loadPersonHeader(BaseIModel.onLoadDateSingleListener listener) {

        Observable<UserInfoDetailBean> observable = ServerApi.getAppAPI().loadPersonInfo(UserInfoManager.getInstance().getAuthcode(), AppConstant.CHANNEL_ID);
        ObservableUtils.parser(observable, new ObservableUtils.Callback<UserInfoDetailBean>() {
            @Override
            public void onSuccess(UserInfoDetailBean bean) {
                listener.onSuccess(bean, BaseIModel.DataType.DATA_ONE);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });

    }

    @Override
    public void loadonRefreshData(int page, onLoadListDateListener listener) {

        Observable<UserInfoListBean> observable = ServerApi.getAppAPI().loadPersonDynamic(UserInfoManager.getInstance().getAuthcode(), page,AppConstant.CHANNEL_ID);
        ObservableUtils.parser(observable, new ObservableUtils.Callback<UserInfoListBean>() {
            @Override
            public void onSuccess(UserInfoListBean bean) {

                listener.onSuccess(bean.getData().getList(), DataType.DATA_ZERO);

            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
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
                        Observable<DefaultDataBean> observable = ServerApi.getAppAPI().getUploadBackground(s, UserInfoManager.getInstance().getAuthcode(),AppConstant.CHANNEL_ID);
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
