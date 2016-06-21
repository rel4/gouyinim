package com.gouyin.im.main.presenter;

import android.graphics.pdf.PdfDocument;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.base.BaseIModel.onLoadDateSingleListener;
import com.gouyin.im.base.BaseIView;
import com.gouyin.im.bean.BaseDataBean;
import com.gouyin.im.bean.UserInfoDetailBean;
import com.gouyin.im.bean.UserInfoListBeanDataList;
import com.gouyin.im.main.model.UserInfoModel;
import com.gouyin.im.main.model.UserInfoModelImpl;
import com.gouyin.im.main.view.UserInfoView;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;

import java.util.List;

/**
 * Created by pc on 2016/6/6.
 */
public class UserInfoPresenterImpl implements UserInfoPresenter, onLoadDateSingleListener<UserInfoDetailBean>, BaseIModel.onLoadListDateListener<UserInfoListBeanDataList> {
    private UserInfoView mUserInfoView;
    private UserInfoModel mUserInfoModel;
    private int page = 1;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(UserInfoView userInfoView) {
        this.mUserInfoView = userInfoView;
        mUserInfoModel = new UserInfoModelImpl();
    }

    @Override
    public void loadonRefreshData(String userId) {
        mUserInfoView.showLoading();
        mUserInfoModel.loadUserInfoData(userId, page, this);
    }

    @Override
    public void loadLoadMoreData(String userId) {

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
            case R.id.tv_send_msg:
                mUserInfoView.switch2SendMsgActivity();
                break;
            case R.id.tv_reward:
                mUserInfoView.switch2RewardActivity();
                break;
        }
    }

    @Override
    public void loadUserInfodetail(String uid) {

        mUserInfoModel.loadUserInfodetail(uid, this);
    }


    @Override
    public void onSuccess(UserInfoDetailBean bean, int type) {
        mUserInfoView.setUserInfodetail(bean);
        mUserInfoView.hideLoading();

    }

    @Override
    public void onSuccess(List<UserInfoListBeanDataList> t, int dataType) {
        mUserInfoView.loadUserinfo(t);
        mUserInfoView.hideLoading();

    }

    @Override
    public void onFailure(String msg, Throwable e) {
        LogUtils.e(this, msg);
        mUserInfoView.hideLoading();
        mUserInfoView.transfePageMsg(UIUtils.getStringRes(R.string.request_failed));

    }


}
