package com.gouyin.im.main.model;

import com.gouyin.im.ServerApi;
import com.gouyin.im.bean.RongyunBean;
import com.gouyin.im.utils.UserInfoUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/6/26.
 */
public class RongyunKeyModelImpl implements RongyunKeyModel {
    @Override
    public void getRongyunKey(onLoadDateSingleListener listener) {

        String authcode = UserInfoUtils.getAuthcode();
        ServerApi.getAppAPI().getRongyunKey(authcode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<RongyunBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e.getMessage(), e);
                    }

                    @Override
                    public void onNext(RongyunBean rongyunBean) {
                        listener.onSuccess(rongyunBean, DataType.DATA_ZERO);
                    }
                });
    }
}
