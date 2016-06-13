package com.gouyin.im.main.widget;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.adapter.UserInfoAdapter;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.bean.UserInfoBean;
import com.gouyin.im.main.presenter.UserInfoPresenter;
import com.gouyin.im.main.presenter.UserInfoPresenterImpl;
import com.gouyin.im.main.view.UserInfoView;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.widget.DividerItemDecoration;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

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
    @Bind(R.id.tv_send_flowers)
    TextView tvSendFlowers;
    private UserInfoPresenter mPresenter;
    private UserInfoAdapter mAdapter;
    private boolean isRefresh;

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setEmptyView(textEmpty);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerview.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        recyclerview.setArrowImageView(R.mipmap.iconfont_downgrey);
        recyclerview.addHeaderView(initHeadLayout());
        recyclerview.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL, Color.GREEN,true));
        recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                mPresenter.loadUserInfoData();
            }

            @Override
            public void onLoadMore() {
                isRefresh = false;
                mPresenter.loadUserInfoData();
            }
        });
        recyclerview.setRefreshing(true);

    }

    /**
     * 初始化头部局
     * @return
     */
    private View initHeadLayout(){
        return UIUtils.inflateLayout(R.layout.head_user_info);
    }

    @Override
    protected View setRootContentView() {
        mPresenter = new UserInfoPresenterImpl(this);
        return UIUtils.inflateLayout(R.layout.activity_userinfo_dynamic);
    }

    @OnClick({R.id.tv_reward, R.id.tv_send_msg, R.id.tv_appointment, R.id.tv_send_flowers})
    public void onClick(View view) {
        mPresenter.switchNavigation(view.getId());
    }

    @Override
    public void loadUserinfo(List<UserInfoBean> list) {

        if (mAdapter == null) {
            mAdapter = new UserInfoAdapter(list);
            recyclerview.setAdapter(mAdapter);
        } else {
            mAdapter.addData(list);
            mAdapter.onRefresh();
        }

    }

    @Override
    public void switch2RewardActivity() {

    }

    @Override
    public void switch2SendMsgActivity() {

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
    public void show() {
        showProgressDialog();
    }

    @Override
    public void hide() {
        hideProgressDialog();
        colseload();
    }
}
