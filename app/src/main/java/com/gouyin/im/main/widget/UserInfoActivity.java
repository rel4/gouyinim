package com.gouyin.im.main.widget;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.gouyin.im.AppConstant;
import com.gouyin.im.R;
import com.gouyin.im.adapter.UserInfoAdapter;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.bean.PayRedPacketPicsBean;
import com.gouyin.im.bean.UserInfoDetailBean;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.main.presenter.UserInfoPresenter;
import com.gouyin.im.main.presenter.UserInfoPresenterImpl;
import com.gouyin.im.main.view.UserInfoView;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.viewholder.UserInfoHeadViewHolder;
import com.gouyin.im.widget.DividerItemDecoration;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pc on 2016/6/3.
 */
public class UserInfoActivity extends BaseActivity implements UserInfoView {

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


    private UserInfoPresenter mPresenter;
    private UserInfoAdapter mAdapter;
    private boolean isRefresh;
    private UserInfoHeadViewHolder headHolder;
    private String userId;

    @Override
    protected void initView() {
        setRxbus();
        userId = getIntent().getStringExtra(AppConstant.USER_ID);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setEmptyView(textEmpty);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerview.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        recyclerview.setArrowImageView(R.mipmap.iconfont_downgrey);
        recyclerview.addHeaderView(initHeadLayout());
        recyclerview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, Color.GREEN, true));
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

    private void setRxbus() {
        RxBus.with(this)
                .setEndEvent(ActivityEvent.DESTROY)
                .setEvent(Events.EventEnum.PAY_SUCCESS_GET_DATA)
                .onNext(events -> {
                    LogUtils.e("MyFragment", "PAY_SUCCESS_GET_DATA 数据");
                    if (events != null && mAdapter != null) {
                        Object message = events.message;
                        if (message instanceof PayRedPacketPicsBean) {
                            PayRedPacketPicsBean bean = (PayRedPacketPicsBean) message;
                            mAdapter.updataPayData(bean);

                        }
                    }
                })
                .create();
    }

    /**
     * 初始化头部局
     *
     * @return
     */
    private View initHeadLayout() {
        View headView = UIUtils.inflateLayout(R.layout.head_user_info);
        headHolder = new UserInfoHeadViewHolder(headView);
        return headView;
    }

    @Override
    protected View setRootContentView() {
        mPresenter = new UserInfoPresenterImpl();
        mPresenter.attachView(this);
        return UIUtils.inflateLayout(R.layout.activity_userinfo_dynamic);
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

        ActivityUtils.startRedpacketActivity(userId, 1, avater);

    }

    @Override
    public void switch2SendMsgActivity() {
        ActivityUtils.startAppConversationActivity(userId, "", "");
    }

    @Override
    public void switch2AppointmentActivity() {
        ActivityUtils.startPayAppointmentActivity();
    }

    @Override
    public void swicth2SendFlowersActivity() {
        ActivityUtils.startRedpacketActivity(userId, 2, avater);
    }

    private void colseload() {
        if (recyclerview == null)
            return;
        if (isRefresh)
            recyclerview.refreshComplete();
        else recyclerview.loadMoreComplete();

    }

    private String avater;

    @Override
    public void setUserInfodetail(UserInfoDetailBean bean) {
        if (bean != null && bean.getData() != null) {
            avater = bean.getData().getBaseinfo().getFace();
        }
        headHolder.setUserInfodetail(bean);
    }

    @Override
    public void pageFinish() {
        finish();
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
