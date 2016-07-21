package com.moonsister.tcjy.main.model;

import com.moonsister.tcjy.AppConstant;
import com.moonsister.tcjy.R;
import com.moonsister.tcjy.ServerApi;
import com.moonsister.tcjy.bean.DefaultDataBean;
import com.moonsister.tcjy.manager.UserInfoManager;
import com.moonsister.tcjy.utils.ObservableUtils;
import com.moonsister.tcjy.utils.StringUtis;
import com.moonsister.tcjy.utils.UIUtils;

import rx.Observable;

/**
 * Created by jb on 2016/7/7.
 */
public class UserActionModelImpl implements UserActionModel {

    @Override
    public void wacthAction(String uid, String type, onLoadDateSingleListener listener) {
        Observable<DefaultDataBean> observable = ServerApi.getAppAPI().getWacthAction(uid, type, UserInfoManager.getInstance().getAuthcode(),AppConstant.CHANNEL_ID);
        ObservableUtils.parser(observable, new ObservableUtils.Callback<DefaultDataBean>() {
            @Override
            public void onSuccess(DefaultDataBean bean) {
                listener.onSuccess(bean, DataType.DATA_ZERO);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }

    /**
     * 点赞
     *
     * @param type      1顶2 取消顶
     * @param dynamicId
     * @param listener
     */
    public void likeAction(String dynamicId, String type, onLoadDateSingleListener<DefaultDataBean> listener) {
        Observable<DefaultDataBean> observable = ServerApi.getAppAPI().getLikeAction(dynamicId, type, UserInfoManager.getInstance().getAuthcode(),AppConstant.CHANNEL_ID);
        ObservableUtils.parser(observable, new ObservableUtils.Callback<DefaultDataBean>() {
            @Override
            public void onSuccess(DefaultDataBean bean) {
                if (bean == null) {
                    listener.onFailure(UIUtils.getStringRes(R.string.request_failed));
                } else {
                    if (StringUtis.equals(bean.getCode(), AppConstant.code_request_success)) {
                        listener.onSuccess(bean, DataType.DATA_ZERO);
                    } else {
                        listener.onFailure(bean.getMsg());
                    }
                }

            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }

    /**
     * 删除动态
     */
    public void deleteDynamic(String id, onLoadDateSingleListener<DefaultDataBean> listener) {
        Observable<DefaultDataBean> observable = ServerApi.getAppAPI().getDelectDynamic(id, UserInfoManager.getInstance().getAuthcode(),AppConstant.CHANNEL_ID);
        ObservableUtils.parser(observable, new ObservableUtils.Callback<DefaultDataBean>() {
            @Override
            public void onSuccess(DefaultDataBean bean) {
                listener.onSuccess(bean, DataType.DATA_ZERO);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }
}
