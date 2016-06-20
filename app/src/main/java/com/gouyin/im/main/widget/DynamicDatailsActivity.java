package com.gouyin.im.main.widget;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.gouyin.im.CacheManager;
import com.gouyin.im.R;
import com.gouyin.im.adapter.DynamiDayailsAdapter;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.BaseDataBean;
import com.gouyin.im.main.presenter.DynamincDatailsPresenter;
import com.gouyin.im.main.presenter.DynamincDatailsPresenterImpl;
import com.gouyin.im.main.view.DynamicDatailsView;
import com.gouyin.im.main.view.UserInfoView;
import com.gouyin.im.utils.ConfigUtils;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.viewholder.UserInfoViewHolder;
import com.gouyin.im.widget.DividerItemDecoration;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pc on 2016/6/7.
 */
public class DynamicDatailsActivity extends BaseActivity implements DynamicDatailsView {

    @Bind(R.id.lv)
    XRecyclerView recyclerView;
    private DynamiDayailsAdapter mAdapter;
    private DynamincDatailsPresenter presenter;

    @Override

    protected void initView() {
        TAG="1.";
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, Color.RED, true));
        recyclerView.setLoadingMoreEnabled(false);
        recyclerView.setPullRefreshEnabled(false);
        if (CacheManager.isExist4DataCache(ConfigUtils.getInstance().getApplicationContext(), TAG + "txt")) {
            Object o = CacheManager.readObject(ConfigUtils.getInstance().getApplicationContext(), TAG + "txt");
            if (o instanceof ArrayList) {
                LogUtils.e(TAG, "CacheManager : " + ((ArrayList<BaseBean>)o).toString());
                loadData((ArrayList<BaseDataBean>)o);
                recyclerView.setRefreshing(true);
            }
        }
        presenter.loadData(TAG + "txt");
//        recyclerView.setAdapter( );
//        lv.setAdapter(new );
    }

    @Override
    protected View setRootContentView() {
        presenter = new DynamincDatailsPresenterImpl(this);
        return UIUtils.inflateLayout(R.layout.activity_dynamic_datails);
    }

    @Override
    protected String initTitleName() {
        return getResources().getString(R.string.activity_name_dynamic_datails);
    }

    @Override
    public void loadData(List<BaseDataBean> datas) {
        if (mAdapter == null) {
            mAdapter = new DynamiDayailsAdapter(datas);
            View view = UIUtils.inflateLayout(R.layout.item_home_one_menu);
            UserInfoViewHolder holder = new UserInfoViewHolder(view, 1);
            recyclerView.addHeaderView(holder.getRootView());
//            layoutContent.addView(holder.getRootView());
            recyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.addData(datas);
            mAdapter.onRefresh();
        }
    }

    @Override
    public void hide() {
        hideProgressDialog();
    }

    @Override
    public void show() {
        showProgressDialog();
    }

}
