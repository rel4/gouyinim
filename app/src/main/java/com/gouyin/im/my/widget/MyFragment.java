package com.gouyin.im.my.widget;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gouyin.im.AppConstant;
import com.gouyin.im.R;
import com.gouyin.im.adapter.UserInfoAdapter;
import com.gouyin.im.base.BaseFragment;
import com.gouyin.im.bean.UserInfoDetailBean;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.main.presenter.UserInfoPresenter;
import com.gouyin.im.main.presenter.UserInfoPresenterImpl;
import com.gouyin.im.main.view.UserInfoView;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.viewholder.PersonDynamicViewholder;
import com.gouyin.im.viewholder.UserInfoHeadViewHolder;
import com.gouyin.im.widget.DividerItemDecoration;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pc on 2016/5/31.
 */
public class MyFragment extends BaseFragment implements UserInfoView {

    @Bind(R.id.recyclerview)
    XRecyclerView recyclerview;
    @Bind(R.id.text_empty)
    TextView textEmpty;
    @Bind(R.id.tv_reward)
    TextView tvReward;
    @Bind(R.id.tv_send_msg)
    TextView tvSendMsg;
    @Bind(R.id.tv_appointment)
    TextView tvAppointment;
    @Bind(R.id.layout_usee_action)
    LinearLayout layout_usee_action;

    private UserInfoPresenter mPresenter;
    private UserInfoAdapter mAdapter;
    private boolean isRefresh;
    //    private UserInfoHeadViewHolder headHolder;
    private String userId;
    private PersonDynamicViewholder personDynamicViewholder;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = new UserInfoPresenterImpl();
        mPresenter.attachView(this);

        return UIUtils.inflateLayout(R.layout.activity_userinfo_dynamic);
    }

    @Override
    protected void initData() {
        layout_usee_action.setVisibility(View.GONE);
//        userId = getIntent().getStringExtra(AppConstant.USER_ID);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setEmptyView(textEmpty);
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
                mPresenter.loadonRefreshData(userId);
            }

            @Override
            public void onLoadMore() {
                isRefresh = false;
                mPresenter.loadLoadMoreData(userId);
            }
        });
        LogUtils.e(this, "userId : " + userId);
        mPresenter.loadUserInfodetail(userId);
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


    @OnClick({R.id.tv_reward, R.id.tv_send_msg, R.id.tv_appointment, R.id.tv_send_flowers})
    public void onClick(View view) {
        mPresenter.switchNavigation(view.getId());
    }

    @Override
    public void loadUserinfo(List<UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList> list) {

        if (mAdapter == null) {
            mAdapter = new UserInfoAdapter(list);
            recyclerview.setAdapter(mAdapter);
        } else {
            mAdapter.addData(list);
            mAdapter.onRefresh();
        }
        loadMoreComplete();
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
    public void switch2RewardActivity() {

        ActivityUtils.startRedpacketActivity(userId, "", "");

    }

    @Override
    public void switch2SendMsgActivity() {
        ActivityUtils.startAppConversationActivity(userId, "", "");
    }

    @Override
    public void switch2AppointmentActivity() {

    }

    @Override
    public void swicth2SendFlowersActivity() {

    }

    private void colseload() {
        if (recyclerview == null)
            return;
        if (isRefresh)
            recyclerview.refreshComplete();
        else recyclerview.loadMoreComplete();

    }

    @Override
    public void setUserInfodetail(UserInfoDetailBean bean) {
        personDynamicViewholder.refreshView(null);
//        headHolder.setUserInfodetail(bean);
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
        colseload();
    }

    @Override
    public void transfePageMsg(String msg) {

    }


}
