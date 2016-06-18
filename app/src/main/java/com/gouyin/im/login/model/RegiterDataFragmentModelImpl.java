package com.gouyin.im.login.model;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.gouyin.im.R;
import com.gouyin.im.ServerApi;
import com.gouyin.im.aliyun.AliyunManager;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.utils.ConfigUtils;
import com.gouyin.im.utils.FilePathUtlis;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.MD5Util;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/6/15.
 */
public class RegiterDataFragmentModelImpl implements RegiterDataFragmentModel {


    @Override
    public void login(String face, String sex, String pwd, String authcode, onLoadDateListener listener) {
//


        ServerApi.getAppAPI().regiterLogin(face, sex, MD5Util.string2MD5(pwd), authcode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.e(RegiterDataFragmentModelImpl.class, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(RegiterDataFragmentModelImpl.class, "Throwable : " + ((Exception) e).getMessage());
                        listener.onFailure(e.getLocalizedMessage(), (Exception) e);

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        LogUtils.e(RegiterDataFragmentModelImpl.class, "onNext : " + baseBean.toString());
                        listener.onSuccess(baseBean, 0);
                    }
                });
    }


    @Override
    public void upLoadIcon(String iconPath, onLoadDateListener listener) {
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
                        listener.onFailure(ConfigUtils.getInstance().getResources().getString(R.string.upload_failure), e);

                    }

                    @Override
                    public void onNext(String host) {
                        listener.onSuccess(host, 1);
                    }
                });

    }
}