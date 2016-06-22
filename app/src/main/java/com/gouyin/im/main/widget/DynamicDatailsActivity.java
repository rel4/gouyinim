package com.gouyin.im.main.widget;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gouyin.im.AppConstant;
import com.gouyin.im.R;
import com.gouyin.im.adapter.DynamiDayailsAdapter;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.bean.BaseDataBean;
import com.gouyin.im.bean.CommentDataListBean;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.main.presenter.DynamincDatailsPresenter;
import com.gouyin.im.main.presenter.DynamincDatailsPresenterImpl;
import com.gouyin.im.main.view.DynamicDatailsView;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.viewholder.UserInfoHeadViewHolder;
import com.gouyin.im.viewholder.UserInfoViewHolder;
import com.gouyin.im.widget.DividerItemDecoration;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by pc on 2016/6/7.
 */
public class DynamicDatailsActivity extends BaseActivity implements DynamicDatailsView {

    @Bind(R.id.lv)
    XRecyclerView recyclerView;
    private DynamiDayailsAdapter mAdapter;
    private DynamincDatailsPresenter presenter;
    private UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList userInfo;

    @Override

    protected void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, Color.RED, true));
        recyclerView.setLoadingMoreEnabled(false);
        recyclerView.setPullRefreshEnabled(false);
        if (userInfo != null) {
            presenter.loadCommentListData(userInfo.getLatest_id());
        }


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
    public void loadData(List<CommentDataListBean.CommentListBean> datas) {
        if (mAdapter == null) {
            mAdapter = new DynamiDayailsAdapter(datas);
            View view = UIUtils.inflateLayout(R.layout.item_home_one_menu);
            UserInfoViewHolder holder = new UserInfoViewHolder(view, userInfo.getType());
            holder.onBindData(userInfo);
            recyclerView.addHeaderView(holder.getRootView());
            recyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.addData(datas);
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
}
