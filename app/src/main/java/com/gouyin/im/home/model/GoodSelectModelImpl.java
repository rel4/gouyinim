package com.gouyin.im.home.model;

import com.gouyin.im.R;
import com.gouyin.im.ServerApi;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.GoodSelectBaen;

import com.gouyin.im.home.widget.GoodSelectFragment;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.ObservableUtils;
import com.gouyin.im.utils.UIUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by pc on 2016/6/3.
 */
public class GoodSelectModelImpl implements GoodSelectModel {

    @Override
    public void loadGoodSelectDate(int pageType, String type, int page, final BaseIModel.onLoadDateSingleListener<List<GoodSelectBaen.Data>> listener) {
        String authcode = UserInfoManager.getInstance().getAuthcode();
        Observable<GoodSelectBaen> observable = null;
        if (pageType == GoodSelectFragment.SAME_CITY) {
            observable = ServerApi.getAppAPI().getSameCity(type, page, authcode);
        } else if (GoodSelectFragment.GOOD_SELECT == pageType) {
            observable = ServerApi.getAppAPI().getGoodSelect(type, page, authcode);
        }
        ObservableUtils.parser(observable, new ObservableUtils.Callback<GoodSelectBaen>() {
            @Override
            public void onSuccess(GoodSelectBaen bean) {
                if (bean != null) {
                    if ("1".equals(bean.getCode()))
                        listener.onSuccess(bean.getData(), BaseIModel.DataType.DATA_ZERO);
                    else
                        listener.onFailure(bean.getMsg());
                } else
                    listener.onFailure(UIUtils.getStringRes(R.string.request_failed));
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }

}
