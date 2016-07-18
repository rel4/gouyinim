package com.moonsister.im.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.moonsister.im.R;
import com.moonsister.im.base.BaseRecyclerViewAdapter;
import com.moonsister.im.base.BaseRecyclerViewHolder;
import com.moonsister.im.bean.TiXinrRecordBean;
import com.moonsister.im.utils.UIUtils;
import com.moonsister.im.viewholder.TixianViewHolder;

import java.util.List;

/**
 * Created by jb on 2016/7/2.
 */
public class TiXianAdapter extends BaseRecyclerViewAdapter<TiXinrRecordBean.DataBean> {

    private TixianViewHolder tixianViewHolder;

    public TiXianAdapter(List<TiXinrRecordBean.DataBean> list) {
        super(list);
    }

    @Override
    protected View initRootView(ViewGroup parent, int viewType) {
        View view = UIUtils.inflateLayout(R.layout.item_tixian);
        tixianViewHolder = new TixianViewHolder(view);
        return tixianViewHolder.getRootView();
    }

    @Override
    protected BaseRecyclerViewHolder getBaseViewHolder(View v, int viewType) {
        return tixianViewHolder;
    }
}
