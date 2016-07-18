package com.moonsister.im.my.widget;

import android.view.View;

import com.moonsister.im.ImageServerApi;
import com.moonsister.im.R;
import com.moonsister.im.base.BaseActivity;
import com.moonsister.im.event.Events;
import com.moonsister.im.event.RxBus;
import com.moonsister.im.manager.UserInfoManager;
import com.moonsister.im.utils.ActivityUtils;
import com.moonsister.im.utils.UIUtils;
import com.moonsister.im.widget.RoundedImageView;
import com.trello.rxlifecycle.ActivityEvent;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 认证
 * Created by jb on 2016/6/27.
 */
public class CertificationActivity extends BaseActivity {
    @Bind(R.id.iv_avater)
    RoundedImageView ivAvater;

    @Override
    protected View setRootContentView() {
        return UIUtils.inflateLayout(R.layout.activity_red_person);
    }

    @Override
    protected String initTitleName() {
        return UIUtils.getStringRes(R.string.red_person_renzheng);
    }

    @Override
    protected void initView() {
        RxBus.with(this)
                .setEndEvent(ActivityEvent.DESTROY)
                .setEvent(Events.EventEnum.CERTIFICATION_PAGE_FINISH)
                .onNext(events ->pageFinish())
                .create();
        ImageServerApi.showURLSamllImage(ivAvater, UserInfoManager.getInstance().getAvater());
    }

    private void pageFinish() {
        finish();
    }

    @OnClick(R.id.tv_apply)
    public void onClick() {
        ActivityUtils.startRZFirstActivity();
    }

}