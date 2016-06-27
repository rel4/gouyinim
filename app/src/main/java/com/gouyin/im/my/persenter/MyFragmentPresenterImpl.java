package com.gouyin.im.my.persenter;

import com.gouyin.im.R;
import com.gouyin.im.my.view.MyFragmentView;

/**
 * Created by jb on 2016/6/27.
 */
public class MyFragmentPresenterImpl implements MyFragmentPresenter {
    private MyFragmentView view;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(MyFragmentView myFragmentView) {
        this.view = myFragmentView;
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
    public void loadonRefreshData(String s) {

    }

    @Override
    public void loadLoadMoreData(String s) {

    }

    @Override
    public void loadUserInfodetail(String s) {

    }


}
