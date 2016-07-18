package com.moonsister.im.my.widget;

import android.view.View;
import android.widget.TextView;

import com.moonsister.im.R;
import com.moonsister.im.base.BaseActivity;
import com.moonsister.im.event.Events;
import com.moonsister.im.event.RxBus;
import com.moonsister.im.utils.ActivityUtils;
import com.moonsister.im.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jb on 2016/6/27.
 */
public class SettingActivity extends BaseActivity {
    @Bind(R.id.timeout)
    TextView timeout;

    @Override
    protected View setRootContentView() {
        return UIUtils.inflateLayout(R.layout.activity_other_setting);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected String initTitleName() {
        return UIUtils.getStringRes(R.string.setting);
    }

    @OnClick({R.id.tv_changepwd, R.id.timeout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.timeout:

                RxBus.getInstance().send(Events.EventEnum.LOGIN_CODE_TIMEOUT, null);
                finish();

                break;
            case R.id.tv_changepwd:
                ActivityUtils.startChangepwdActivity();
                break;
        }


    }


}