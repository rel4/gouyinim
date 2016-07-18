package com.moonsister.im.main.model;

import com.moonsister.im.AppConstant;
import com.moonsister.im.R;
import com.moonsister.im.ServerApi;
import com.moonsister.im.bean.RongyunBean;
import com.moonsister.im.event.Events;
import com.moonsister.im.event.RxBus;
import com.moonsister.im.manager.UserInfoManager;
import com.moonsister.im.utils.StringUtis;
import com.moonsister.im.utils.UIUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/6/26.
 */
public class RongyunKeyModelImpl implements RongyunKeyModel {
    @Override
    public void getRongyunKey(onLoadDateSingleListener listener) {

        String authcode = UserInfoManager.getInstance().getAuthcode();
        ServerApi.getAppAPI().getRongyunKey(authcode,AppConstant.CHANNEL_ID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<RongyunBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(RongyunBean rongyunBean) {
                        if (rongyunBean != null) {
                            String code = rongyunBean.getCode();
                            if (StringUtis.equals(code, AppConstant.code_timeout)) {
                                listener.onFailure(UIUtils.getStringRes(R.string.code_timeout));
                                RxBus.getInstance().send(Events.EventEnum.LOGIN, null);
                            } else if (StringUtis.equals(code, AppConstant.code_request_success)) {
                                listener.onSuccess(rongyunBean, DataType.DATA_ZERO);
                            } else
                                listener.onFailure(UIUtils.getStringRes(R.string.request_failed));
                        }

                    }
                });
    }
}
