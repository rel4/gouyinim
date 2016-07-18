package com.moonsister.im.my.widget;

import android.view.View;

import com.moonsister.im.R;
import com.moonsister.im.base.BaseActivity;
import com.moonsister.im.utils.UIUtils;

/**
 * 申诉
 * Created by jb on 2016/6/27.
 */
public class RefundActivity extends BaseActivity {
    @Override
    protected View setRootContentView() {
        return UIUtils.inflateLayout(R.layout.activity_complain);
    }

    @Override
    protected void initView() {

    }
}
