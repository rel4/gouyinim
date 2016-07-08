package com.gouyin.im.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.BaseDataBean;

import butterknife.ButterKnife;


/**
 * Created by pc on 2016/6/4.
 */
public abstract class BaseRecyclerViewHolder<T extends BaseDataBean> extends RecyclerView.ViewHolder {
    private View mRootView;

    public BaseRecyclerViewHolder(View view) {
        super(view);
        this.mRootView = view;
        ButterKnife.bind(this, view);
    }

    /**
     * 设置点击事件
     *
     * @param t
     * @param position
     */
    public void setOnClick(final T t, final int position) {
        if (mRootView != null)
            mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemclick(v, t, position);
                    if (listener != null)
                        listener.onItemclick(v, position);
                }
            });
    }

    /**
     * 得到根布局
     *
     * @return
     */
    public View getRootView() {
        return mRootView;
    }

    protected abstract void onBindData(T t);

    public void onBindData(T t, int position) {
    }

    /**
     * @param view     点击的view
     * @param t        点击的对象数据
     * @param position 点击位置
     */
    protected abstract void onItemclick(View view, T t, int position);

    private onItemClickListener listener;

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }


    public interface onItemClickListener {
        void onItemclick(View view, int position);
    }
}
