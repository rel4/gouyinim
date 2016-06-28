package com.gouyin.im.main.model;

import com.gouyin.im.AppConstant;
import com.gouyin.im.R;
import com.gouyin.im.ServerApi;
import com.gouyin.im.bean.RongyunBean;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;
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
                        if (rongyunBean != null) {
                            String code = rongyunBean.getCode();
                            if (StringUtis.equals(code, AppConstant.code_timeout)) {
                                listener.onFailure(UIUtils.getStringRes(R.string.code_timeout), null);
                                RxBus.getInstance().send(Events.EventEnum.LOGIN, null);
                            } else if (StringUtis.equals(code, AppConstant.code_request_success)) {
                                listener.onSuccess(rongyunBean, DataType.DATA_ZERO);
                            } else
                                listener.onFailure(UIUtils.getStringRes(R.string.request_failed), null);
                        }

                    }
                });
    }
}