package com.gouyin.im.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.BaseDataBean;
import com.gouyin.im.bean.PayRedPacketPicsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/6/4.
 */
public abstract class BaseRecyclerViewAdapter<T extends BaseDataBean> extends RecyclerView.Adapter {
    protected List<T> datas;
//    private int layoutID;

    public BaseRecyclerViewAdapter(List<T> list) {
        this.datas = list;
//        this.layoutID = layoutID;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = initRootView(parent, viewType);
        return getBaseViewHolder(v, viewType);
    }

    protected abstract View initRootView(ViewGroup parent, int viewType);

    protected abstract BaseRecyclerViewHolder getBaseViewHolder(View v, int viewType);


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseRecyclerViewHolder) {
            BaseRecyclerViewHolder bvh = (BaseRecyclerViewHolder) holder;
            bvh.onBindData(datas.get(position));
            bvh.setOnClick(datas.get(position), position);
        }


    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void addData(List<T> list) {
        if (datas == null)
            datas = new ArrayList<T>();
        datas.addAll(list);
    }

    public void onRefresh() {
        notifyDataSetChanged();
    }

}
