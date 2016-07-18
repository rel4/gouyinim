package com.moonsister.im.my.widget;

import android.view.View;

import com.moonsister.im.R;
import com.moonsister.im.base.BaseActivity;
import com.moonsister.im.event.Events;
import com.moonsister.im.event.RxBus;
import com.moonsister.im.utils.UIUtils;

import butterknife.OnClick;

/**
 * Created by jb on 2016/6/27.
 */
public class RZThidActivity extends BaseActivity {
    @Override
    protected View setRootContentView() {
        return UIUtils.inflateLayout(R.layout.activity_rzthid);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected String initTitleName() {
        return UIUtils.getStringRes(R.string.apply_renzheng);
    }

    @OnClick(R.id.tv_success)
    public void onClick() {
        RxBus.getInstance().send(Events.EventEnum.CERTIFICATION_PAGE_FINISH, null);
        finish();
    }
}
