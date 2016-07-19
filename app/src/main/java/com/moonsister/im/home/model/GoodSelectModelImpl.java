package com.moonsister.im.home.model;

import com.moonsister.im.AppConstant;
import com.moonsister.im.R;
import com.moonsister.im.ServerApi;
import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.bean.GoodSelectBaen;

import com.moonsister.im.home.widget.GoodSelectFragment;
import com.moonsister.im.manager.UserInfoManager;
import com.moonsister.im.utils.ObservableUtils;
import com.moonsister.im.utils.UIUtils;

import java.util.List;

import rx.Observable;


/**
 * Created by pc on 2016/6/3.
 */
public class GoodSelectModelImpl implements GoodSelectModel {

    @Override
    public void loadGoodSelectDate(int pageType, String type, int page, final BaseIModel.onLoadDateSingleListener<List<GoodSelectBaen.Data>> listener) {
        String authcode = UserInfoManager.getInstance().getAuthcode();
        Observable<GoodSelectBaen> observable = null;
        if (pageType == GoodSelectFragment.SAME_CITY) {
            observable = ServerApi.getAppAPI().getSameCity(type, page, authcode, AppConstant.CHANNEL_ID);
        } else if (GoodSelectFragment.GOOD_SELECT == pageType) {
            observable = ServerApi.getAppAPI().getGoodSelect(type, page, authcode, AppConstant.CHANNEL_ID);
        }
        if (observable == null) {
            listener.onFailure(UIUtils.getStringRes(R.string.request_failed));
            return;
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
