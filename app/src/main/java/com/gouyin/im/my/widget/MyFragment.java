package com.gouyin.im.my.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.adapter.UserDynamicAdapter;
import com.gouyin.im.base.BaseFragment;
import com.gouyin.im.bean.PayRedPacketPicsBean;
import com.gouyin.im.bean.UserInfoDetailBean;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.my.persenter.MyFragmentPresenter;
import com.gouyin.im.my.persenter.MyFragmentPresenterImpl;
import com.gouyin.im.my.view.MyFragmentView;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.viewholder.PersonDynamicViewholder;
import com.gouyin.im.widget.XListView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pc on 2016/5/31.
 */
public class MyFragment extends BaseFragment implements MyFragmentView {

    @Bind(R.id.tv_certification)
    TextView tv_certification;
    @Bind(R.id.recyclerview)
    XListView recyclerview;
    private MyFragmentPresenter mPresenter;
    private UserDynamicAdapter mAdapter;
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


    public void updataPayData(PayRedPacketPicsBean bean) {
        if (mAdapter != null) {
            mAdapter.updataPayData(bean);
        }
    }

    @Override
    protected void initData() {
        int certification = UserInfoManager.getInstance().getCertificationStatus();
        /**
         * 认证状态 1 已认证  2 认证中  3 未认证
         */
        if (certification == 1) {
            tv_certification.setText(UIUtils.getStringRes(R.string.already_Certification));
        } else if (certification == 2) {
            tv_certification.setText(UIUtils.getStringRes(R.string.Certificationing));
        } else
            tv_certification.setText(UIUtils.getStringRes(R.string.not_Certificationing));

        recyclerview.setVerticalLinearLayoutManager();
        recyclerview.addHeaderView(initHeadLayout());
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
        RxBus.with(this)
                .setEndEvent(FragmentEvent.DESTROY)
                .setEvent(Events.EventEnum.CROP_IMAGE_PATH)
                .onNext(events -> {
                    if (events != null && personDynamicViewholder != null) {
                        String path = (String) events.message;
                        mPresenter.uploadBackground(path);
                    }
                })
                .create();
        personDynamicViewholder = new PersonDynamicViewholder();
        return personDynamicViewholder.getContentView();
    }

    @Override
    public void setUserBackground(String path) {
        if (personDynamicViewholder != null)
            personDynamicViewholder.upImage(path);
    }

    @OnClick({R.id.tv_certification, R.id.tv_withdraw_deposit, R.id.tv_appointment, R.id.tv_person_order, R.id.tv_person_setting})
    public void onClick(View view) {
        mPresenter.swicth2Page(view.getId());

    }


    private void loadMoreComplete() {
        if (recyclerview == null)
            return;
        recyclerview.loadMoreComplete();
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

    /**
     * 认证
     */
    @Override
    public void swich2PersonRed() {
        int certification = UserInfoManager.getInstance().getCertificationStatus();
        /**
         * 认证状态 1 已认证  2 认证中  3 未认证
         */
        if (certification == 1) {
            String stringRes = UIUtils.getStringRes(R.string.already_Certification);
            String certificationStatus = tv_certification.getText().toString().trim();
            if (!StringUtis.equals(certificationStatus, stringRes))
                tv_certification.setText(stringRes);
            showToast(stringRes);
        } else if (certification == 2) {
            String str = UIUtils.getStringRes(R.string.Certificationing);
            String certificationStatus = tv_certification.getText().toString().trim();
            if (!StringUtis.equals(certificationStatus, str))
                tv_certification.setText(str);
            ActivityUtils.startRZThidActivity();
        } else

            ActivityUtils.startCertificationActivity();


    }

    /**
     * 提现
     */
    @Override
    public void swich2WithdRawDeposit() {
        ActivityUtils.startWithdRawDepositActivity();
    }

    /**
     * 约会
     */
    @Override
    public void swich2Appointment() {
        ActivityUtils.startAppointmentActivity();
    }

    /**
     * 订单
     */
    @Override
    public void swich2PersonOrder() {
        ActivityUtils.startMyOrderActivity();
    }

    /**
     * 设置
     */
    @Override
    public void swich2PersonSetting() {
        ActivityUtils.startSettingActivity();
    }

    @Override
    public void setListData(List<UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList> list) {
        if (mAdapter == null) {
            mAdapter = new UserDynamicAdapter(list);
            recyclerview.setAdapter(mAdapter);
        } else {
            if (isRefresh)
                mAdapter.clean();
            mAdapter.addListData(list);
            mAdapter.onRefresh();
        }
        loadMoreComplete();
    }

    @Override
    public void setUserInfo(UserInfoDetailBean bean) {

        personDynamicViewholder.refreshView(bean);
    }


}
