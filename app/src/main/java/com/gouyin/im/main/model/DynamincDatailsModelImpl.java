package com.gouyin.im.main.model;

import android.os.Looper;
import android.os.SystemClock;

import com.gouyin.im.CacheManager;
import com.gouyin.im.ServerApi;
import com.gouyin.im.base.onLoadDateListener;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.GoodSelectBaen;
import com.gouyin.im.utils.ConfigUtils;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by pc on 2016/6/8.
 */
public class DynamincDatailsModelImpl implements DynamincDatailsModel {

    private String TAG =this.getClass().getSimpleName();
    @Override
    public void loadData(final String fileName , final onLoadDateListener listener) {

        Observable<GoodSelectBaen> login = ServerApi.getAppAPI().login("haha", "hahh");
//        login.observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Action1<GoodSelectBaen>() {
//                    @Override
//                    public void call(GoodSelectBaen baen) {
//                        LogUtils.e(TAG,"onNext : "+baen.toString());
//                        LogUtils.e(TAG,"是否在主线程："+(Thread.currentThread()== Looper.getMainLooper().getThread()));
//
//                        ArrayList<BaseBean> beans = new ArrayList<>();
//                        for (int i = 0; i < 10; i++) {
//                        beans.add(baen);
//                        }
//
//                        listener.onSuccess(beans);
//                    }
//                });
        login.observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<GoodSelectBaen>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.e(TAG,"onCompleted ");
                        LogUtils.e(TAG,"是否在主线程："+(Thread.currentThread()== Looper.getMainLooper().getThread()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(TAG,"Throwable : "+e.getMessage());
                        LogUtils.e(TAG,"是否在主线程："+(Thread.currentThread()== Looper.getMainLooper().getThread()));
                    }

                    @Override
                    public void onNext(GoodSelectBaen baen) {
                        LogUtils.e(TAG,"onNext : "+baen.toString());
                        LogUtils.e(TAG,"是否在主线程："+(Thread.currentThread()== Looper.getMainLooper().getThread()));

                        final ArrayList<BaseBean> beans = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            beans.add(baen);
                        }
                        CacheManager.saveObject(ConfigUtils.getInstance().getApplicationContext(),beans,fileName);
                        SystemClock.sleep(4000);
                        UIUtils.onRunMainThred(new Runnable() {
                            @Override
                            public void run() {
                                listener.onSuccess(beans);
                            }
                        });

                    }
                });

    }
}
