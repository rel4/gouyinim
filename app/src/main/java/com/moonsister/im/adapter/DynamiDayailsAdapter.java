package com.moonsister.im.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.moonsister.im.R;
import com.moonsister.im.base.BaseRecyclerViewAdapter;
import com.moonsister.im.base.BaseRecyclerViewHolder;
import com.moonsister.im.bean.CommentDataListBean;
import com.moonsister.im.utils.UIUtils;
import com.moonsister.im.viewholder.DynamicCommentViewHolder;

import java.util.List;

/**
 * Created by pc on 2016/6/8.
 */
public class DynamiDayailsAdapter extends BaseRecyclerViewAdapter<CommentDataListBean.DataBean> {


    public DynamiDayailsAdapter(List<CommentDataListBean.DataBean> list) {
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
