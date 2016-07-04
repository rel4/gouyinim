package com.gouyin.im.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseRecyclerViewAdapter;
import com.gouyin.im.base.BaseRecyclerViewHolder;
import com.gouyin.im.bean.CardInfoBean;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.viewholder.SwitchCardViewHolder;

import java.util.List;

import butterknife.Bind;

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
