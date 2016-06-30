package com.gouyin.im.my.widget;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gouyin.im.R;
import com.gouyin.im.adapter.UserInfoAdapter;
import com.gouyin.im.base.BaseFragment;
import com.gouyin.im.bean.PayRedPacketPicsBean;
import com.gouyin.im.bean.UserInfoDetailBean;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.my.persenter.MyFragmentPresenter;
import com.gouyin.im.my.persenter.MyFragmentPresenterImpl;

import com.gouyin.im.my.view.MyFragmentView;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.viewholder.PersonDynamicViewholder;
import com.gouyin.im.widget.DividerItemDecoration;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pc on 2016/5/31.
 */
public class MyFragment extends BaseFragment implements MyFragmentView {


    @Bind(R.id.recyclerview)
    XRecyclerView recyclerview;
    private MyFragmentPresenter mPresenter;
    private UserInfoAdapter mAdapter;
    private boolean isRefresh;

    private PersonDynamicViewholder personDynamicViewholder;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = new MyFragmentPresenterImpl();
        mPresenter.attachView(this);

        return UIUtils.inflateLayout(R.layout.activity_person_dynamic);
    }

    /**
     *
     */
    private void setRxbus() {


    }

    public void updataPayData(PayRedPacketPicsBean bean) {
        if (mAdapter != null) {
            mAdapter.updataPayData(bean);
        }
    }

    @Override
    protected void initData() {
        setRxbus();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerview.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        recyclerview.setArrowImageView(R.mipmap.iconfont_downgrey);
        recyclerview.addHeaderView(initHeadLayout());
        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, Color.GREEN, true));
        recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                mPresenter.loadonRefreshData();
            }

            @Override
            public void onLoadMore() {
                isRefresh = false;
                mPresenter.loadLoadMoreData();
            }
        });
        LogUtils.e(this, "userId : " + "");
        mPresenter.loadPersonHeader();
        recyclerview.setRefreshing(true);

    }


    /**
     * 初始化头部局
     *
     * @return
     */
    private View initHeadLayout() {
        personDynamicViewholder = new PersonDynamicViewholder();
        return personDynamicViewholder.getContentView();
    }

    @OnClick({R.id.tv_certification, R.id.tv_withdraw_deposit, R.id.tv_appointment, R.id.tv_person_order, R.id.tv_person_setting})
    public void onClick(View view) {
        mPresenter.swicth2Page(view.getId());

    }


    private void loadMoreComplete() {
        if (recyclerview == null)
            return;
        if (!isRefresh)
            recyclerview.loadMoreComplete();
        else
            recyclerview.refreshComplete();

    }


    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();

    }

    @Override
    public void transfePageMsg(String msg) {
        showToast(msg);
    }


    @Override
    public void swich2PersonRed() {
        ActivityUtils.startCertificationActivity();
    }

    @Override
    public void swich2WithdRawDeposit() {
        ActivityUtils.startWithdRawDepositActivity();
    }

    @Override
    public void swich2Appointment() {
        ActivityUtils.startAppointmentActivity();
    }

    @Override
    public void swich2PersonOrder() {
        ActivityUtils.startMyOrderActivity();
    }

    @Override
    public void swich2PersonSetting() {
        ActivityUtils.startSettingActivity();
    }

    @Override
    public void setListData(List<UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList> list) {
        if (mAdapter == null) {
            mAdapter = new UserInfoAdapter(list);
            recyclerview.setAdapter(mAdapter);
        } else {
            mAdapter.addData(list);
            mAdapter.onRefresh();
        }
        loadMoreComplete();
    }

    @Override
    public void setUserInfo(UserInfoDetailBean bean) {
        personDynamicViewholder.refreshView(bean);
    }
}
