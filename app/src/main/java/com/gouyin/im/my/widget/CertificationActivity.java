package com.gouyin.im.my.widget;

import android.view.View;
import android.widget.ImageView;

import com.gouyin.im.ImageServerApi;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jb on 2016/6/27.
 */
public class CertificationActivity extends BaseActivity {
    @Bind(R.id.iv_avater)
    ImageView ivAvater;

    @Override
    protected View setRootContentView() {
        return UIUtils.inflateLayout(R.layout.activity_red_person);
    }

    @Override
    protected void initView() {

        ImageServerApi.showURLSamllImage(ivAvater, UserInfoManager.getInstance().getAvater());
    }


    @OnClick(R.id.tv_apply)
    public void onClick() {
        ActivityUtils.startRZFirstActivity();
    }

}
