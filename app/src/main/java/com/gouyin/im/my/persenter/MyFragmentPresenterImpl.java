package com.gouyin.im.my.persenter;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.UserInfoDetailBean;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.my.model.MyFragmentModel;
import com.gouyin.im.my.model.MyFragmentModelImpl;
import com.gouyin.im.my.view.MyFragmentView;

import java.util.List;

/**
 * Created by jb on 2016/6/27.
 */
public class MyFragmentPresenterImpl implements MyFragmentPresenter, BaseIModel.onLoadListDateListener<UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList>, BaseIModel.onLoadDateSingleListener<UserInfoDetailBean> {
    private MyFragmentView view;
    private MyFragmentModel model;
    private int page = 2;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(MyFragmentView myFragmentView) {
        this.view = myFragmentView;
        model = new MyFragmentModelImpl();
    }

    @Override
    public void swicth2Page(int id) {
        switch (id) {
            case R.id.tv_certification:
                view.swich2PersonRed();
                break;
            case R.id.tv_withdraw_deposit:
                view.swich2WithdRawDeposit();
                break;
            case R.id.tv_appointment:
                view.swich2Appointment();
                break;
            case R.id.tv_person_order:
                view.swich2PersonOrder();
                break;
            case R.id.tv_person_setting:
                view.swich2PersonSetting();
                break;
        }
    }

    @Override
    public void loadPersonHeader() {
        view.showLoading();
        model.loadPersonHeader(this);
    }


    @Override
    public void loadonRefreshData() {
        view.showLoading();
        model.loadonRefreshData(1, this);

    }

    @Override
    public void loadLoadMoreData() {
        view.showLoading();
        model.loadonRefreshData(page, this);
        page++;
    }


    @Override
    public void onSuccess(List<UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList> t, BaseIModel.DataType dataType) {
        switch (dataType) {
            case DATA_ZERO:
                view.setListData(t);
                break;
        }
        view.hideLoading();

    }

    @Override
    public void onSuccess(UserInfoDetailBean bean, BaseIModel.DataType dataType) {

        view.setUserInfo(bean);
    }

    @Override
    public void onFailure(String msg, Throwable e) {
        view.hideLoading();
        view.transfePageMsg(msg);
    }
}
