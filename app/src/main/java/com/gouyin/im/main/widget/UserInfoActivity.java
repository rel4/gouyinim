package com.gouyin.im.main.widget;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gouyin.im.AppConstant;
import com.gouyin.im.ImageServerApi;
import com.gouyin.im.R;
import com.gouyin.im.adapter.UserInfoAdapter;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.bean.UserInfoBean;
import com.gouyin.im.bean.UserInfoDetailBean;
import com.gouyin.im.bean.UserInfoListBeanDataList;
import com.gouyin.im.main.presenter.UserInfoPresenter;
import com.gouyin.im.main.presenter.UserInfoPresenterImpl;
import com.gouyin.im.main.view.UserInfoView;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.widget.DividerItemDecoration;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
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
    private ViewHolder headHolder;
    private String userId;

    @Override
    protected void initView() {
        userId = getIntent().getStringExtra(AppConstant.USER_ID);
        userId = "104002";
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

    /**
     * 初始化头部局
     *
     * @return
     */
    private View initHeadLayout() {
        View headView = UIUtils.inflateLayout(R.layout.head_user_info);
        headHolder = new ViewHolder(headView);
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
    public void loadUserinfo(List<UserInfoListBeanDataList> list) {

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
    public void setUserInfodetail(UserInfoDetailBean bean) {
        headHolder.setUserInfodetail(bean);
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

    static class ViewHolder {

        @Bind(R.id.user_background)
        ImageView userBackground;
        @Bind(R.id.iv_user_icon)
        ImageView ivUserIcon;
        @Bind(R.id.tv_user_name)
        TextView tvUserName;
        @Bind(R.id.tv_fen_number)
        TextView tvFenNumber;
        @Bind(R.id.tv_wacth_number)
        TextView tvWacthNumber;
        @Bind(R.id.tv_dynamic_number)
        TextView tvDynamicNumber;
        @Bind(R.id.tv_flower_number)
        TextView tvFlowerNumber;
        @Bind(R.id.tv_wacth)
        TextView tvWacth;
        private UserInfoDetailBean userInfodetail;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setUserInfodetail(UserInfoDetailBean userInfodetail) {
            if (userInfodetail == null || userInfodetail.getData() == null)
                return;
            UserInfoDetailBean.UserInfoDetailDataBean data = userInfodetail.getData();
            UserInfoDetailBean.UserInfoDetailDataBean.Addons addons = data.getAddons();
            UserInfoDetailBean.UserInfoDetailDataBean.Baseinfo baseinfo = data.getBaseinfo();


            ImageServerApi.showURLImage(userBackground, baseinfo.getLikeImage());
            ImageServerApi.showURLImage(ivUserIcon, baseinfo.getFace());
            tvUserName.setText(baseinfo.getNickname());
            tvFenNumber.setText(addons.getUfann());
            tvDynamicNumber.setText(addons.getUlatn());
            tvFlowerNumber.setText(addons.getUfann());
            tvWacthNumber.setText(addons.getUfoln());
            tvWacth.setText(getwacthStatus(data.getFollow()));
        }

        private String getwacthStatus(String str) {
            String status = null;
            if ("1".equals(str)) {
                status = UIUtils.getStringRes(R.string.already) + UIUtils.getStringRes(R.string.wacth);
            } else if ("2".equals(str))
                status = UIUtils.getStringRes(R.string.one_another) + UIUtils.getStringRes(R.string.wacth);
            else if ("3".equals(str))
                status = UIUtils.getStringRes(R.string.no) + UIUtils.getStringRes(R.string.wacth);

            return status;
        }

    }
}
