package com.gouyin.im.base;

import android.support.v7.widget.RecyclerView;

import android.view.View;

import com.gouyin.im.bean.BaseBean;

import butterknife.ButterKnife;


/**
 * Created by pc on 2016/6/4.
 */
public abstract class BaseViewHolder<T extends BaseBean> extends RecyclerView.ViewHolder {
    private View mRootView;

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

    /**
     * 得到根布局
     * @return
     */
    public View getRootView(){
        return  mRootView;
    }
    protected abstract void onBindData(T t);

    protected abstract void onItemclick(View view, int position);

}
