package com.gouyin.im.im.widget;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.adapter.FrientAdapter;
import com.gouyin.im.base.BaseFragment;
import com.gouyin.im.bean.FrientBaen;
import com.gouyin.im.im.prsenter.FrientFragmentPresenter;
import com.gouyin.im.im.prsenter.FrientFragmentPresenterImpl;
import com.gouyin.im.my.view.FrientFragmentView;
import com.gouyin.im.utils.FragmentUtils;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.widget.XListView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jb on 2016/6/20.
 */
public class FrientFragment extends BaseFragment implements FrientFragmentView {
    @Bind(R.id.tv_wacth)
    TextView tvWacth;
    @Bind(R.id.tv_fen)
    TextView tvFen;
    @Bind(R.id.layout_head)
    RelativeLayout layout_head;
    @Bind(R.id.fl_frient_content)
    FrameLayout fl_frient_content;
    @Bind(R.id.layout_root)
    LinearLayout layout_root;
    private XListView xListView;
    public static final int PAGE_WACTH = 1;
    public static final int PAGE_FEN = 2;
    public static final int PAGE_MAIN = 3;
    private int pageType;
    private FrientFragmentPresenter presenter;
    private FrientAdapter adapter;
    private boolean isLoadMord;

    public void setPageType(int pageType) {
        this.pageType = pageType;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UIUtils.inflateLayout(R.layout.fragme_frient_main, container);
    }

    @Override
    protected void initData() {
        if (pageType == PAGE_MAIN) {
            layout_head.setVisibility(View.VISIBLE);
            onClick(tvWacth);
        } else {
            layout_head.setVisibility(View.GONE);
            initXListView();
        }

    }

    private void initXListView() {
        presenter = new FrientFragmentPresenterImpl();
        presenter.attachView(this);
        xListView = new XListView(getContext());
        xListView.setVerticalLinearLayoutManager();
        layout_root.removeView(fl_frient_content);
        layout_root.addView(xListView);
        xListView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isLoadMord = false;
                presenter.loadRefresh(pageType);
            }

            @Override
            public void onLoadMore() {
                isLoadMord = true;
                presenter.loadBasicData(pageType);
            }
        });
        ViewGroup.LayoutParams layoutParams = xListView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        xListView.setLayoutParams(layoutParams);
        presenter.loadBasicData(pageType);
    }


    public static FrientFragment newInstance() {
        return new FrientFragment();
    }


    @OnClick({R.id.tv_wacth, R.id.tv_fen})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_wacth:
                switchPage(PAGE_WACTH);
                break;
            case R.id.tv_fen:
                switchPage(PAGE_FEN);
                break;
        }
    }

    private FrientFragment currenFragment, fenFragment, wacthFragment;

    private void switchPage(int flag) {
        FrientFragment fragment = null;
        if (flag == PAGE_WACTH) {
            if (wacthFragment == null) {
                wacthFragment = FrientFragment.newInstance();
                wacthFragment.setPageType(PAGE_WACTH);

            }
            fragment = wacthFragment;
        } else if (flag == PAGE_FEN) {
            if (fenFragment == null) {
                fenFragment = FrientFragment.newInstance();
                fenFragment.setPageType(PAGE_FEN);
            }
            fragment = fenFragment;
        }
        if (fragment != null) {
            FragmentUtils.switchHideFragment(getChildFragmentManager(), R.id.fl_frient_content, currenFragment, fragment);
        }
        currenFragment = fragment;
        tvFen.setSelected(fragment == fenFragment);
        tvWacth.setSelected(fragment == wacthFragment);
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

    @Override
    public void setBasicData(FrientBaen frientBaen) {
        if (frientBaen == null) {
            xListView.setNoMore();
            xListView.loadMoreComplete();
            xListView.refreshComplete();
            return;
        }
        if (frientBaen.getData() == null || frientBaen.getData().size() == 0) {
            xListView.setNoMore();
            xListView.loadMoreComplete();
            xListView.refreshComplete();
            return;
        }
        if (adapter == null) {
            adapter = new FrientAdapter(frientBaen.getData(), this);
            if (xListView != null)
                xListView.setAdapter(adapter);
        } else {
            if (!isLoadMord)
                adapter.clean();
            adapter.addListData(frientBaen.getData());
            adapter.onRefresh();
        }
        xListView.loadMoreComplete();
        xListView.refreshComplete();
    }
}
