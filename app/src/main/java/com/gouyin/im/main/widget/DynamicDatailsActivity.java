package com.gouyin.im.main.widget;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gouyin.im.AppConstant;
import com.gouyin.im.R;
import com.gouyin.im.adapter.DynamiDayailsAdapter;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.bean.CommentDataListBean;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.main.presenter.DynamincDatailsPresenter;
import com.gouyin.im.main.presenter.DynamincDatailsPresenterImpl;
import com.gouyin.im.main.view.DynamicDatailsView;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.viewholder.UserInfoViewHolder;
import com.gouyin.im.widget.XListView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pc on 2016/6/7.
 */
public class DynamicDatailsActivity extends BaseActivity implements DynamicDatailsView {

    @Bind(R.id.lv)
    XListView recyclerView;
    @Bind(R.id.ed_input)
    EditText edInput;
    private DynamiDayailsAdapter mAdapter;
    private DynamincDatailsPresenter presenter;
    private UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList userInfo;

    @Override

    protected void initView() {

        recyclerView.setVerticalLinearLayoutManager();
        recyclerView.setPullRefreshEnabled(false);
        if (userInfo != null) {
            presenter.loadCommentListData(userInfo.getLatest_id());
        }
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                presenter.loadCommentListData(userInfo.getLatest_id());
            }
        });


    }

    @Override
    protected View setRootContentView() {
        userInfo = (UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList) getIntent().getSerializableExtra(AppConstant.DYNAMIC_DATAILS);
        if (userInfo == null) {
            finish();
            return null;
        }
        LogUtils.e(this, "userInfo id : " + userInfo.getUid());
        presenter = new DynamincDatailsPresenterImpl();
        presenter.attachView(this);
        return UIUtils.inflateLayout(R.layout.activity_dynamic_datails);
    }

    @Override
    protected String initTitleName() {
        return getResources().getString(R.string.activity_name_dynamic_datails);
    }

    @Override
    public void loadData(List<CommentDataListBean.DataBean> datas) {
        if (mAdapter == null) {
            mAdapter = new DynamiDayailsAdapter(datas);
            View view = UIUtils.inflateLayout(R.layout.item_home_one_menu);
            UserInfoViewHolder holder = new UserInfoViewHolder(view, userInfo.getType());
            holder.onBindData(userInfo);
            recyclerView.addHeaderView(holder.getRootView());
            recyclerView.setAdapter(mAdapter);
        } else {

            mAdapter.addListData(datas);
            if (datas != null && datas.size() == 0) {
                recyclerView.setNoMore();
            }
        }
        if (recyclerView != null) {
            recyclerView.loadMoreComplete();
        }
    }

    @Override
    public void CommentSuccess() {
        CommentDataListBean.DataBean dataBean = new CommentDataListBean.DataBean();

        dataBean.setFace(UserInfoManager.getInstance().getAvater());
        dataBean.setNickname(UserInfoManager.getInstance().getNickeName());
        dataBean.setCreate_time(System.currentTimeMillis() / 1000);
        dataBean.setTitle(s);
        if (mAdapter != null) {

            mAdapter.addSingeData(0, dataBean);
            mAdapter.onRefresh();
        }
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

    }

    private String s;

    @OnClick(R.id.btn_send)
    public void onClick() {
        s = edInput.getText().toString();
        if (StringUtis.isEmpty(s)) {
            showToast(UIUtils.getStringRes(R.string.input) + UIUtils.getStringRes(R.string.not_empty));
            return;
        }
        if (s.length() > 100) {
            showToast(UIUtils.getStringRes(R.string.input_text_number_100));
            return;
        }
        presenter.sendComment(userInfo.getLatest_id(), s, "");
        edInput.setText("");
    }
}
