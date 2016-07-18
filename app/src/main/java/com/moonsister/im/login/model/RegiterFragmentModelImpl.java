package com.moonsister.im.login.model;

import com.moonsister.im.AppConstant;
import com.moonsister.im.ServerApi;
import com.moonsister.im.bean.BaseBean;
import com.moonsister.im.bean.DefaultDataBean;
import com.moonsister.im.bean.RegiterBean;
import com.moonsister.im.utils.JsonUtils;
import com.moonsister.im.utils.LogUtils;
import com.moonsister.im.utils.ObservableUtils;
import com.moonsister.im.utils.PhoneInfoUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pc on 2016/6/14.
 */
public class RegiterFragmentModelImpl implements RegiterFragmentModel {
    private String TAG = getClass().getSimpleName();


    @Override
    public void loadSubmit(String phoneNumber, String code, onLoadSubmitListenter listenter) {

        ServerApi.getAppAPI().verifySecurityCode(phoneNumber, code, AppConstant.CHANNEL_ID).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())

                .subscribe(new Subscriber<RegiterBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(TAG, "Throwable : " + e.getMessage().toString());
                        listenter.onSubmitFailure(e.getLocalizedMessage(), (Exception) e);
                    }

                    @Override
                    public void onNext(RegiterBean baseBean) {
                        LogUtils.e(TAG, "onNext : " + baseBean.toString());
                        listenter.onSubmitSuccess(baseBean);
                    }
                });
    }

    @Override
    public void loadSecurity(String phoneMunber, final onLoadDateSingleListener listener) {
        uploadPhoneInfo(phoneMunber);
        Observable<BaseBean> observable = ServerApi.getAppAPI().sendSecurityCode(phoneMunber, AppConstant.CHANNEL_ID);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.e(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(TAG, "Throwable : " + ((Exception) e).getMessage());
                        listener.onFailure(e.getLocalizedMessage());

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        LogUtils.e(TAG, "onNext : " + baseBean.toString());
                        listener.onSuccess(baseBean, DataType.DATA_ZERO);
                    }
                });
    }

    private void uploadPhoneInfo(String phoneMunber) {
        PhoneInfoUtils phoneInfoUtils = PhoneInfoUtils.newInstance();
        phoneInfoUtils.setTel2(phoneMunber);
        String serialize = JsonUtils.serialize(phoneInfoUtils);
        LogUtils.e(this, serialize);
        Observable<DefaultDataBean> observable = ServerApi.getAppAPI().getuploadPhoneInfo(serialize);
        ObservableUtils.parser(observable, new ObservableUtils.Callback() {
            @Override
            public void onSuccess(BaseBean bean) {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }


}