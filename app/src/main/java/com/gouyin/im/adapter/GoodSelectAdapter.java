package com.gouyin.im.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseRecyclerViewAdapter;
import com.gouyin.im.base.BaseRecyclerViewHolder;
import com.gouyin.im.bean.GoodSelectBaen;
import com.gouyin.im.viewholder.GoodSelectViewHolder;
import com.gouyin.im.widget.RoundedImageView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by pc on 2016/6/4.
 */
public class GoodSelectAdapter extends BaseRecyclerViewAdapter<GoodSelectBaen.Data> {



    public GoodSelectAdapter(List list) {
        super(list);
    }

    @Override
    protected View initRootView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_good_select, parent, false);
    }

    @Override
    protected BaseRecyclerViewHolder getBaseViewHolder(View v, int viewType) {
        return new GoodSelectViewHolder(v);
    }
}
