package com.moonsister.tcjy.center.widget;

import android.support.v4.app.Fragment;

import com.moonsister.tcjy.bean.model.BaseFragmentActivity;

/**
 * Created by jb on 2016/8/8.
 */
public class DynamicPublishActivity extends BaseFragmentActivity {
    @Override
    protected String initTitleName() {
        return super.initTitleName();
    }

    @Override
    protected Fragment initFragment() {
        return DynamicPublishFragment.newInstance();
    }

    @Override
    public boolean isBaseonActivityResult() {
        return false;
    }
}
