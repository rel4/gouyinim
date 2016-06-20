package com.gouyin.im.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseRecyclerViewAdapter;
import com.gouyin.im.base.BaseRecyclerViewHolder;
import com.gouyin.im.bean.BaseDataBean;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.viewholder.DynamicCommentViewHolder;

import java.util.List;

/**
 * Created by pc on 2016/6/8.
 */
public class DynamiDayailsAdapter  extends BaseRecyclerViewAdapter<BaseDataBean>{

    public DynamiDayailsAdapter(List<BaseDataBean> list) {
        super(list);
    }

    @Override
    protected View initRootView(ViewGroup parent, int viewType) {
        return UIUtils.inflateLayout(R.layout.item_comment);
    }

    @Override
    protected BaseRecyclerViewHolder getBaseViewHolder(View v, int viewType) {
        return new DynamicCommentViewHolder(v);
    }
}
