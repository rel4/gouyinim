package com.gouyin.im.login.model;

import com.gouyin.im.ServerApi;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.RegiterBean;
import com.gouyin.im.utils.LogUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.schedulers.Schedulers;

/**
 * Created by pc on 2016/6/14.
 */
public class RegiterFragmentModelImpl implements RegiterFragmentModel {
    private String TAG = getClass().getSimpleName();


    @Override
    public void loadSubmit(String phoneNumber, String code, onLoadSubmitListenter listenter) {
        ServerApi.getAppAPI().verifySecurityCode(phoneNumber, code).observeOn(AndroidSchedulers.mainThread())
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
    public void loadSecurity(String phoneMunber, final onLoadDateListener listener) {
        Observable<BaseBean> observable = ServerApi.getAppAPI().sendSecurityCode(phoneMunber);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.e(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(TAG, "Throwable : " +( (Exception)e).getMessage());
                        listener.onFailure(e.getLocalizedMessage(), (Exception) e);

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        LogUtils.e(TAG, "onNext : " + baseBean.toString());
                        listener.onSuccess(baseBean,0);
                    }
                });
    }


}
