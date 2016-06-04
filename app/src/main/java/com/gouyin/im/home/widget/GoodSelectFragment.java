package com.gouyin.im.home.widget;

import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseFragment;
import com.gouyin.im.bean.GoodSelectBaen;
import com.gouyin.im.home.adapter.MyAdapter;

import com.gouyin.im.home.presenetr.GoodSelectPresenter;
import com.gouyin.im.home.presenetr.GoodSelectPresenterImpl;
import com.gouyin.im.home.view.GoodSelectView;
import com.gouyin.im.widget.SpacesItemDecoration;
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
    XRecyclerView recyclerview;
    @Bind(R.id.text_empty)
    TextView textEmpty;
    private ProgressDialog progressDialog;
    private GoodSelectPresenter goodSelectPresenter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        goodSelectPresenter = new GoodSelectPresenterImpl(this);
        return inflater.inflate(R.layout.fragment_good_select, container, false);
    }

    @Override
    protected void initData() {
        progressDialog = new ProgressDialog(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerview.setLayoutManager(gridLayoutManager);
        recyclerview.addItemDecoration(new SpacesItemDecoration(10));
        recyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);


        recyclerview.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        recyclerview.setArrowImageView(R.mipmap.iconfont_downgrey);
        recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isloadMore=false;
                goodSelectPresenter.loadGoodSelectDateList();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        recyclerview.refreshComplete();
//                    }
//                }, 5000);
            }

            @Override
            public void onLoadMore() {
                isloadMore=true;
                goodSelectPresenter.loadGoodSelectDateList();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        recyclerview.loadMoreComplete();
//                    }
//                }, 5000);
            }
        });
        goodSelectPresenter.loadGoodSelectDateList();
    }

    private MyAdapter mAdapter;
    private int index;
    private boolean isloadMore =false;
    @Override
    public void addGoodSelectDate(List<GoodSelectBaen> list) {

        ArrayList listData = new ArrayList<String>();
        for (int i = 0; i < 15; i++) {
            index =index+i;
            listData.add("item" + (index + listData.size()));

        }
        if (mAdapter == null) {
            mAdapter = new MyAdapter(listData);
            recyclerview.setAdapter(mAdapter);
            recyclerview.setRefreshing(true);
        } else {
            mAdapter.addData(listData);
        }
        loadMoreComplete();


    }

    @Override
    public void show() {
        if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.show();
    }

    @Override
    public void hide() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();

    }
    private void loadMoreComplete(){
        if (recyclerview==null)
            return;
        if (isloadMore)
            recyclerview.loadMoreComplete();
        else
            recyclerview.refreshComplete();

    }
}
