package com.gouyin.im.my.widget;

import android.view.View;
import android.widget.GridView;

import com.gouyin.im.R;
import com.gouyin.im.adapter.RZGridViewAdapter;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jb on 2016/6/27.
 */
public class RZSecondActivity extends BaseActivity {
    @Bind(R.id.grid_view)
    GridView gridView;

    @Override
    protected View setRootContentView() {
        return UIUtils.inflateLayout(R.layout.activity_rzsecond);
    }

    @Override
    protected void initView() {
        gridView.setAdapter(new RZGridViewAdapter(this));
    }



    @OnClick(R.id.tv_submit)
    public void onClick() {
        ActivityUtils.startActivity(RZThidActivity.class);
    }
}
