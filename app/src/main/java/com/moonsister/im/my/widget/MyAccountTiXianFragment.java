package com.moonsister.im.my.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moonsister.im.R;
import com.moonsister.im.adapter.TiXianAdapter;
import com.moonsister.im.base.BaseFragment;
import com.moonsister.im.bean.TiXinrRecordBean;
import com.moonsister.im.event.Events;
import com.moonsister.im.event.RxBus;
import com.moonsister.im.my.persenter.TiXianFragmentPresenter;
import com.moonsister.im.my.persenter.TiXianFragmentPresenterImpl;
import com.moonsister.im.my.view.TiXianFragmentView;
import com.moonsister.im.utils.UIUtils;
import com.moonsister.im.widget.XListView;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import butterknife.Bind;

/**
 * Created by jb on 2016/6/27.
 */
public class MyAccountTiXianFragment extends BaseFragment implements TiXianFragmentView {
    @Bind(R.id.list)
    XListView xListView;
    private TiXianFragmentPresenter presenter;
    private TiXianAdapter adapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new TiXianFragmentPresenterImpl();
        presenter.attachView(this);
        return UIUtils.inflateLayout(R.layout.fragment_my_account_recorder, container);
    }

    @Override
    protected void initData() {
        xListView.setVerticalLinearLayoutManager();
        xListView.setRefreshing(false);
        xListView.setLoadingMoreEnabled(false);
        presenter.loadTixin();
        RxBus.with(this)
                .setEndEvent(FragmentEvent.DESTROY)
                .setEvent(Events.EventEnum.MONEY_CHANGE)
                .onNext(events -> {
                    presenter.loadTixin();
                })
                .create();
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
    public void setLoadData(TiXinrRecordBean bean) {
        List<TiXinrRecordBean.DataBean> data = bean.getData();
        adapter = new TiXianAdapter(bean.getData());
        xListView.setAdapter(adapter);

    }
}
