package com.gouyin.im.home.widget;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gouyin.im.CacheManager;
import com.gouyin.im.R;
import com.gouyin.im.adapter.GoodSelectAdapter;
import com.gouyin.im.base.BaseFragment;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.GoodSelectBaen;
import com.gouyin.im.home.presenetr.GoodSelectPresenter;
import com.gouyin.im.home.presenetr.GoodSelectPresenterImpl;
import com.gouyin.im.home.view.GoodSelectView;
import com.gouyin.im.utils.ConfigUtils;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.widget.SpacesItemDecoration;
import com.gouyin.im.widget.XListView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * Created by pc on 2016/6/1.
 */
public class GoodSelectFragment extends BaseFragment implements GoodSelectView {
    @Bind(R.id.recyclerview)
    XListView recyclerview;
    @Bind(R.id.text_empty)
    TextView textEmpty;
    private static int pageType;
    private GoodSelectPresenter goodSelectPresenter;
    public static final int GOOD_SELECT = 1;
    public static final int SAME_CITY = 2;

    public static BaseFragment newInstance(int type) {
        pageType = type;
        return new GoodSelectFragment();
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        goodSelectPresenter = new GoodSelectPresenterImpl(this);
        return inflater.inflate(R.layout.fragment_good_select, container, false);
    }

    @Override
    protected void initData() {

        recyclerview.setVerticalGridLayoutManager(2);
        recyclerview.setEmptyView(textEmpty);

        recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isloadMore = false;
                goodSelectPresenter.uploadGoodSelectDateList(pageType);

            }

            @Override
            public void onLoadMore() {
                isloadMore = true;
                goodSelectPresenter.downloadGoodSelectDateList(pageType);

            }
        });
        recyclerview.setRefreshing(true);
    }

    private GoodSelectAdapter mAdapter;
    private boolean isloadMore = false;

    @Override
    public void addGoodSelectDate(List<GoodSelectBaen.Data> list) {

        if (mAdapter == null) {
            mAdapter = new GoodSelectAdapter(list);
            if (recyclerview != null)
                recyclerview.setAdapter(mAdapter);
        } else {
            if (!isloadMore)
                mAdapter.clean();
            mAdapter.addListData(list);
            mAdapter.onRefresh();

        }
        loadMoreComplete();


    }

    private void loadMoreComplete() {
        if (recyclerview == null)
            return;
        if (isloadMore)
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
}
