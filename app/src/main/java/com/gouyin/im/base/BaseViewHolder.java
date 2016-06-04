package com.gouyin.im.base;

import android.support.v7.widget.RecyclerView;

import android.view.View;

import com.gouyin.im.bean.BaseBean;

import butterknife.ButterKnife;


/**
 * Created by pc on 2016/6/4.
 */
public abstract class BaseViewHolder<T extends BaseBean> extends RecyclerView.ViewHolder {
    protected View mRootView;

    public BaseViewHolder(View view) {
        super(view);
        this.mRootView = view;
        ButterKnife.bind(this, view);
    }

    public void setOnClick(final int position) {
        if (mRootView != null)
            mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemclick(v, position);
                }
            });
    }

    protected abstract void onBindData(T t);

    protected abstract void onItemclick(View view, int position);

}
