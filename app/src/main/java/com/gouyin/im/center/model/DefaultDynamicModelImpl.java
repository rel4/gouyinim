package com.gouyin.im.center.model;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.gouyin.im.ServerApi;
import com.gouyin.im.aliyun.AliyunManager;
import com.gouyin.im.base.ImageObjoct;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.center.presenter.DefaultDynamicPresenterImpl;
import com.gouyin.im.utils.FilePathUtlis;
import com.gouyin.im.utils.JsonUtils;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UserInfoUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/6/23.
 */
public class DefaultDynamicModelImpl implements DefaultDynamicModel {
    @Override
    public void sendDynamicPics(String content, List<String> srcdatas, List<String> fuzzys, String address, onLoadDateSingleListener listener) {
        LogUtils.e(DefaultDynamicModelImpl.this, "start upload");
        ArrayList<ImageObjoct> aliyunPtahs = new ArrayList<ImageObjoct>();
        Observable.create(new Observable.OnSubscribe<ArrayList<ImageObjoct>>() {
            @Override
            public void call(Subscriber<? super ArrayList<ImageObjoct>> subscriber) {
                try {
                    for (int i = 0; i < srcdatas.size(); i++) {
                        ImageObjoct image = new ImageObjoct();
                        String loadFile = AliyunManager.getInstance().upLoadFile(srcdatas.get(i), FilePathUtlis.FileType.JPG);
                        if (fuzzys != null) {
                            if (fuzzys.size() <= i) {
                                String fuzzyFile = AliyunManager.getInstance().upLoadFile(fuzzys.get(i), FilePathUtlis.FileType.JPG);
                                image.setS(fuzzyFile);
                            } else
                                image.setS("");
                        } else {
                            image.setS("");
                        }
                        image.setL(loadFile);
                        aliyunPtahs.add(image);
                    }

                    subscriber.onNext(aliyunPtahs);
                    String serialize = JsonUtils.serialize(aliyunPtahs);
                    LogUtils.e(DefaultDynamicModelImpl.this, "aliyunPtahs --JsonUtils : " + serialize);
                } catch (ClientException e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                } catch (ServiceException e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                }
            }
        }).observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ArrayList<ImageObjoct>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(DefaultDynamicModelImpl.this, "onError :　" + e.getMessage());
                        e.printStackTrace();
                        listener.onFailure(e.getMessage(), e);
                    }

                    @Override
                    public void onNext(ArrayList<ImageObjoct> s) {
                        LogUtils.e(DefaultDynamicPresenterImpl.class, "  onNext :　" + aliyunPtahs.toString());
                        String serialize = JsonUtils.serialize(s);
                        LogUtils.e(DefaultDynamicModelImpl.this, "JsonUtils : " + serialize);
                        sendAllDynamic(content, serialize, address, listener);
                    }
                });
    }

    private void sendAllDynamic(String content, String json, String address, onLoadDateSingleListener listener) {
        String authcode = UserInfoUtils.getAuthcode();
        ServerApi.getAppAPI().sendAllDefaultDynamic("1", content, json, "", address, authcode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e.getMessage(), e);
                    }

                    @Override
                    public void onNext(BaseBean bean) {
                        listener.onSuccess(bean, DataType.DATA_ZERO);
                    }
                });

    }

}
