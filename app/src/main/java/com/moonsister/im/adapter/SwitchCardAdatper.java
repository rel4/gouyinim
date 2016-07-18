package com.moonsister.im.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.moonsister.im.R;
import com.moonsister.im.base.BaseRecyclerViewAdapter;
import com.moonsister.im.base.BaseRecyclerViewHolder;
import com.moonsister.im.bean.CardInfoBean;
import com.moonsister.im.utils.UIUtils;
import com.moonsister.im.viewholder.SwitchCardViewHolder;

import java.util.List;

/**
 * Created by jb on 2016/7/4.
 */
public class SwitchCardAdatper extends BaseRecyclerViewAdapter<CardInfoBean.DataBean> {


    public SwitchCardAdatper(List<CardInfoBean.DataBean> list) {
        super(list);
    }

    private SwitchCardViewHolder holder;

    @Override
    protected View initRootView(ViewGroup parent, int viewType) {
        View view = UIUtils.inflateLayout(R.layout.itme_switch_card, parent);
        holder = new SwitchCardViewHolder(view);
        return holder.getRootView();
    }

    @Override
    protected BaseRecyclerViewHolder getBaseViewHolder(View v, int viewType) {
        return holder;
    }

    @Override
    public void onItemclick(View view, int position) {
        for (int i = 0; i < datas.size(); i++) {
            CardInfoBean.DataBean dataBean = datas.get(i);
            if (i == position) {

                dataBean.setIs_default("1");
            } else {
                dataBean.setIs_default("0");
            }
        }
        notifyDataSetChanged();

    }
}
