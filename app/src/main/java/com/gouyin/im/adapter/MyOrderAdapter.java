package com.gouyin.im.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseRecyclerViewAdapter;
import com.gouyin.im.base.BaseRecyclerViewHolder;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.viewholder.MyOrderViewHolder;

import java.util.List;

/**
 * Created by jb on 2016/6/27.
 */
public class MyOrderAdapter extends BaseRecyclerViewAdapter {
    private MyOrderViewHolder viewHolder;

    public MyOrderAdapter(List list) {
        super(list);
    }

    @Override
    protected View initRootView(ViewGroup parent, int viewType) {
        View view = UIUtils.inflateLayout(R.layout.item_order, parent);
        viewHolder = new MyOrderViewHolder(view);
        return viewHolder.getRootView();
    }

    @Override
    protected BaseRecyclerViewHolder getBaseViewHolder(View v, int viewType) {
        return viewHolder;
    }
}
