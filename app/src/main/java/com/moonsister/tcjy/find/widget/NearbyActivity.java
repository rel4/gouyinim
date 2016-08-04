package com.moonsister.tcjy.find.widget;

import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.moonsister.tcjy.R;
import com.moonsister.tcjy.adapter.NearbyAdapter;
import com.moonsister.tcjy.base.BaseActivity;
import com.moonsister.tcjy.bean.NearbyBean;
import com.moonsister.tcjy.find.presenter.NearbyActivityPresenter;
import com.moonsister.tcjy.find.presenter.NearbyActivityPresenterImpl;
import com.moonsister.tcjy.find.view.NearbyActivityView;
import com.moonsister.tcjy.utils.UIUtils;
import com.moonsister.tcjy.widget.XListView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by jb on 2016/8/4.
 */
public class NearbyActivity extends BaseActivity implements NearbyActivityView {
    private NearbyActivityPresenter presenter;
    private NearbyAdapter nearbyAdapter;
    private boolean isRefresh;
    private String sex = "0";
    @Bind(R.id.xlv)
    XListView xlv;

    @Override
    protected View setRootContentView() {
        presenter = new NearbyActivityPresenterImpl();
        presenter.attachView(this);
        return UIUtils.inflateLayout(R.layout.activity_nearby);
    }

    @Override
    protected String initTitleName() {
        return UIUtils.getStringRes(R.string.nearby);
    }

    @Override
    protected void initView() {
        xlv.setVerticalGridLayoutManager(3);
        xlv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                presenter.refresh(sex);
            }

            @Override
            public void onLoadMore() {
                isRefresh = false;
                presenter.loadMore(sex);
            }
        });
        xlv.setRefreshing(true);
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
        if (xlv != null) {
            xlv.refreshComplete();
            xlv.loadMoreComplete();
        }
    }

    @Override
    public void transfePageMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void setData(List<NearbyBean.DataBean> data) {
        if (nearbyAdapter == null) {
            nearbyAdapter = new NearbyAdapter(data);
            xlv.setAdapter(nearbyAdapter);
        } else {
            if (isRefresh)
                nearbyAdapter.clean();
            nearbyAdapter.addListData(data);
        }
    }
}
