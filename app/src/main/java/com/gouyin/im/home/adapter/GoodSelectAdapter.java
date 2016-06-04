package com.gouyin.im.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseRecyclerViewAdapter;
import com.gouyin.im.base.BaseViewHolder;
import com.gouyin.im.bean.GoodSelectBaen;

import java.util.List;

/**
 * Created by pc on 2016/6/4.
 */
public class GoodSelectAdapter extends BaseRecyclerViewAdapter <GoodSelectBaen>{

    public GoodSelectAdapter(List list) {
        super(list);
    }

    @Override
    protected View initRootView(ViewGroup parent, int viewType) {
        return  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_one_menu, parent, false);
    }

    @Override
    protected BaseViewHolder getBaseViewHolder(View v,int viewType) {
        return new GoodSelectViewHolder(v);
    }
}
