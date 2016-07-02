package com.gouyin.im.home.model;

import com.gouyin.im.R;
import com.gouyin.im.ServerApi;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.GoodSelectBaen;

import com.gouyin.im.utils.UIUtils;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by pc on 2016/6/3.
 */
public class GoodSelectModelImpl implements GoodSelectModel {

    @Override
    public void loadGoodSelectDate(String type, int page, final BaseIModel.onLoadDateSingleListener<List<GoodSelectBaen.Data>> listener) {
        ServerApi.getAppAPI().getGoodSelect(type, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<GoodSelectBaen>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(GoodSelectBaen goodSelectBaen) {
                        if (goodSelectBaen != null) {
                            if ("1".equals(goodSelectBaen.getCode()))
                                listener.onSuccess(goodSelectBaen.getData(), BaseIModel.DataType.DATA_ZERO);
                            else
                                listener.onFailure(goodSelectBaen.getMsg());
                        } else
                            listener.onFailure(UIUtils.getStringRes(R.string.request_failed));
                    }
                });
    }

}
