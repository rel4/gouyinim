package com.gouyin.im.main.model;

import android.os.Looper;
import android.os.SystemClock;

import com.gouyin.im.CacheManager;
import com.gouyin.im.ServerApi;
import com.gouyin.im.base.BaseIModel;
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

    private String TAG = this.getClass().getSimpleName();

    @Override
    public void loadData(final String fileName, final BaseIModel.onLoadDateListener listener) {

        listener.onSuccess(new BaseBean(), 0);

    }
}
