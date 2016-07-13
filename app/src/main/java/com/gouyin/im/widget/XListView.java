package com.gouyin.im.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

import com.gouyin.im.R;
import com.gouyin.im.widget.SpacesItemDecoration;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by jb on 2016/6/27.
 */
public class XListView extends XRecyclerView {
    private Context mContext;

    public XListView(Context context) {
        super(context);
        this.mContext = context;
    }

    public XListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public XListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    /**
     * 竖向GridView
     *
     * @param number
     */
    public void setVerticalGridLayoutManager(int number) {
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, number);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        this.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.HORIZONTAL));
        this.setLayoutManager(layoutManager);
        this.addItemDecoration(new SpacesItemDecoration(20));
        this.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        this.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        this.setArrowImageView(R.mipmap.iconfont_downgrey);


    }

    /**
     * 竖向listView
     */
    public void setVerticalLinearLayoutManager() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        this.setLayoutManager(layoutManager);
//        this.addItemDecoration(new SpacesItemDecoration(2));
        this.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        this.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        this.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        this.setArrowImageView(R.mipmap.iconfont_downgrey);
    }

    public void setNoMore() {
        this.setIsnomore(true);
        this.noMoreLoading();
    }

}
