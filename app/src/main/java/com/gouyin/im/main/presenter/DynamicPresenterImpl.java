package com.gouyin.im.main.presenter;

import com.gouyin.im.AppConstant;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.base.BaseIModel.onLoadDateSingleListener;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.bean.UserInfoDetailBean;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.main.model.UserActionModelImpl;
import com.gouyin.im.main.model.UserInfoModel;
import com.gouyin.im.main.model.UserInfoModelImpl;
import com.gouyin.im.main.view.DynamicView;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

import java.util.List;

/**
 * Created by pc on 2016/6/6.
 */
public class DynamicPresenterImpl implements DynamicPresenter, onLoadDateSingleListener<UserInfoDetailBean>, BaseIModel.onLoadListDateListener<UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList> {
    private DynamicView mUserInfoView;
    private UserInfoModel mUserInfoModel;
    private int page = 2;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(DynamicView userInfoView) {
        this.mUserInfoView = userInfoView;
        mUserInfoModel = new UserInfoModelImpl();
    }

    @Override
    public void loadonRefreshData(String userId) {
        mUserInfoView.showLoading();
        mUserInfoModel.loadUserInfoData(userId, 1, this);
    }

    @Override
    public void loadLoadMoreData(String userId) {
        mUserInfoView.showLoading();
        mUserInfoModel.loadUserInfoData(userId, page, this);
        page++;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.tv_appointment:
                mUserInfoView.switch2AppointmentActivity();
                break;
            case R.id.tv_send_flowers:
                mUserInfoView.swicth2SendFlowersActivity();
                break;
            case R.id.single_send_msg:
            case R.id.tv_send_msg:
                mUserInfoView.switch2SendMsgActivity();
                break;
            case R.id.tv_reward:
                mUserInfoView.switch2RewardActivity();
                break;
            case R.id.action_back:
                mUserInfoView.pageFinish();
                break;
        }
    }

    @Override
    public void loadUserInfodetail(String uid) {
        mUserInfoView.showLoading();
        mUserInfoModel.loadUserInfodetail(uid, this);
    }

    @Override
    public void deleteDynamic(String id) {
        mUserInfoView.showLoading();
        UserActionModelImpl actionModel = new UserActionModelImpl();
        actionModel.deleteDynamic(id, new BaseIModel.onLoadDateSingleListener<DefaultDataBean>() {
            @Override
            public void onSuccess(DefaultDataBean bean, BaseIModel.DataType dataType) {
                if (StringUtis.equals(bean.getCode(), AppConstant.code_request_success)) {
                    mUserInfoView.deleteDynamic(id);
                }
                mUserInfoView.transfePageMsg(bean.getMsg());
                mUserInfoView.hideLoading();
            }

            @Override
            public void onFailure(String msg) {
                mUserInfoView.transfePageMsg(msg);
                mUserInfoView.hideLoading();
            }
        });
    }


    @Override
    public void onSuccess(UserInfoDetailBean bean, BaseIModel.DataType type) {
        mUserInfoView.setUserInfodetail(bean);
        mUserInfoView.hideLoading();

    }

    @Override
    public void onSuccess(List<UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList> t, BaseIModel.DataType dataType) {
        mUserInfoView.loadUserinfo(t);
        mUserInfoView.hideLoading();

    }

    @Override
    public void onFailure(String msg) {
        LogUtils.e(this, msg);
        mUserInfoView.hideLoading();
        mUserInfoView.transfePageMsg(UIUtils.getStringRes(R.string.request_failed));

    }


}