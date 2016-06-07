package com.gouyin.im.main.widget;

import android.view.View;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.utils.UIUtils;

/**
 * Created by pc on 2016/6/7.
 */
public class DynamicDatailsActivity extends BaseActivity {

    @Override
    protected void initView() {

    }

    @Override
    protected View setRootContentView() {
        return UIUtils.inflateLayout(R.layout.activity_dynamic_datails);
    }

    @Override
    protected String initTitleName() {
        return getResources().getString(R.string.activity_name_dynamic_datails);
    }
}
