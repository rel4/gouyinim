package com.gouyin.im.my.persenter;

import com.gouyin.im.AppConstant;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.bean.UserInfoDetailBean;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.my.model.MyFragmentModel;
import com.gouyin.im.my.model.MyFragmentModelImpl;
import com.gouyin.im.my.view.MyFragmentView;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

import java.util.List;

/**
 * Created by jb on 2016/6/27.
 */
public class MyFragmentPresenterImpl implements MyFragmentPresenter, BaseIModel.onLoadListDateListener<UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList>, BaseIModel.onLoadDateSingleListener {
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
        model.loadPersonHeader(this);
    }


    @Override
    public void loadonRefreshData() {
        view.showLoading();
        model.loadonRefreshData(1, this);
        page = 2;

    }

    @Override
    public void loadLoadMoreData() {
        view.showLoading();
        model.loadonRefreshData(page, this);
        page++;
    }

    @Override
    public void uploadBackground(String path) {
        view.showLoading();
        model.uploadBackground(path, this);
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
    public void onSuccess(Object obj, BaseIModel.DataType dataType) {

        if (obj == null) {
            view.hideLoading();
            view.transfePageMsg(UIUtils.getStringRes(R.string.request_failed));
            return;
        }
        switch (dataType) {
            case DATA_TWO:
                DefaultDataBean dd = (DefaultDataBean) obj;
                if (StringUtis.equals(AppConstant.code_request_success, dd.getCode())) {
                    view.setUserBackground((String) dd.getObj());
                } else {
                    view.transfePageMsg(dd.getMsg());
                }
                view.hideLoading();
                break;
            case DATA_ONE:
                UserInfoDetailBean bean = (UserInfoDetailBean) obj;
                view.setUserInfo(bean);
                if (bean != null && !StringUtis.equals(bean.getCode(), AppConstant.code_request_success))
                    view.transfePageMsg(bean.getMsg());
                break;
        }


    }

    @Override
    public void onFailure(String msg) {
        view.hideLoading();
        view.transfePageMsg(msg);
    }
}
