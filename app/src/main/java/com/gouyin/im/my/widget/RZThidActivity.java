package com.gouyin.im.my.widget;

import android.view.View;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.utils.UIUtils;

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


    @OnClick(R.id.tv_success)
    public void onClick() {
        RxBus.getInstance().send(Events.EventEnum.CERTIFICATION_PAGE_FINISH, null);
        finish();
    }
}
