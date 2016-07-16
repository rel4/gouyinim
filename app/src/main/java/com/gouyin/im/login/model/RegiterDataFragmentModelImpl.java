package com.gouyin.im.login.model;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.gouyin.im.AppConstant;
import com.gouyin.im.R;
import com.gouyin.im.ServerApi;
import com.gouyin.im.manager.aliyun.AliyunManager;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.utils.ConfigUtils;
import com.gouyin.im.utils.FilePathUtlis;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.MD5Util;
import com.gouyin.im.utils.ObservableUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/6/15.
 */
public class RegiterDataFragmentModelImpl implements RegiterDataFragmentModel {


    @Override
    public void login(String face, String sex, String pwd, String authcode, onLoadDateSingleListener listener) {
//


        Observable<BaseBean> observable = ServerApi.getAppAPI().regiterLogin(face, sex, MD5Util.string2MD5(pwd), AppConstant.CHANNEL_ID, authcode);
        ObservableUtils.parser(observable, new ObservableUtils.Callback() {
            @Override
            public void onSuccess(BaseBean bean) {
                listener.onSuccess(bean, DataType.DATA_ZERO);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }


    @Override
    public void upLoadIcon(String iconPath, onLoadDateSingleListener listener) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String host = AliyunManager.getInstance().upLoadFile(iconPath, FilePathUtlis.FileType.JPG);
                    subscriber.onNext(host);
                } catch (ClientException e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                } catch (ServiceException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(ConfigUtils.getInstance().getResources().getString(R.string.upload_failure));

                    }

                    @Override
                    public void onNext(String host) {
                        listener.onSuccess(host, DataType.DATA_ONE);
                    }
                });

    }
}
