package com.gouyin.im.home.widget;


import im.gouyin.com.progressdialog.ProgressDialog;


import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;


import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseFragment;
import com.gouyin.im.bean.GoodSelectBaen;
import com.gouyin.im.adapter.GoodSelectAdapter;

import com.gouyin.im.home.presenetr.GoodSelectPresenter;
import com.gouyin.im.home.presenetr.GoodSelectPresenterImpl;
import com.gouyin.im.home.view.GoodSelectView;
import com.gouyin.im.utils.ConfigUtils;
import com.gouyin.im.widget.SpacesItemDecoration;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import java.util.List;


import butterknife.Bind;


/**
 * Created by pc on 2016/6/1.
 */
public class GoodSelectFragment extends BaseFragment implements GoodSelectView {
    @Bind(R.id.recyclerview)
    XRecyclerView recyclerview;
    @Bind(R.id.text_empty)
    TextView textEmpty;

    private GoodSelectPresenter goodSelectPresenter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        goodSelectPresenter = new GoodSelectPresenterImpl(this);
        return inflater.inflate(R.layout.fragment_good_select, container, false);
    }

    @Override
    protected void initData() {

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerview.setLayoutManager(layoutManager);
        recyclerview.addItemDecoration(new SpacesItemDecoration(10));
        recyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerview.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        recyclerview.setArrowImageView(R.mipmap.iconfont_downgrey);
        recyclerview.setEmptyView(textEmpty);
        recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isloadMore = false;
                goodSelectPresenter.loadGoodSelectDateList();

            }

            @Override
            public void onLoadMore() {
                isloadMore = true;
                goodSelectPresenter.loadGoodSelectDateList();

            }
        });
        goodSelectPresenter.loadGoodSelectDateList();
    }

    private GoodSelectAdapter mAdapter;
    private int index;
    private boolean isloadMore = false;

    @Override
    public void addGoodSelectDate(List<GoodSelectBaen> list) {

        if (mAdapter == null) {
            mAdapter = new GoodSelectAdapter(list);
            recyclerview.setAdapter(mAdapter);
            recyclerview.setRefreshing(true);
        } else {
            mAdapter.addData(list);

        }
        loadMoreComplete();


    }

    @Override
    public void show() {
        showProgressDialog();
    }

    @Override
    public void hide() {
      hideProgressDialog();
    }

    private void loadMoreComplete() {
        if (recyclerview == null)
            return;
        if (isloadMore)
            recyclerview.loadMoreComplete();
        else
            recyclerview.refreshComplete();

    }
}
