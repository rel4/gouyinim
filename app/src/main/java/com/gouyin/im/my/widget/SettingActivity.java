package com.gouyin.im.my.widget;

import android.os.Bundle;
import android.view.View;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jb on 2016/6/27.
 */
public class SettingActivity extends BaseActivity {
    @Override
    protected View setRootContentView() {
        return UIUtils.inflateLayout(R.layout.activity_other_setting);
    }

    @Override
    protected void initView() {

    }


    @OnClick(R.id.tv_changepwd)
    public void onClick() {
        ActivityUtils.startChangepwdActivity();
    }
}
