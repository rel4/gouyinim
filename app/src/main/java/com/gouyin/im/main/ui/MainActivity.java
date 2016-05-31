package com.gouyin.im.main.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.utils.FragmentUtils;

import butterknife.Bind;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @Bind(R.id.tv_home_page)
    TextView tvHomePage;
    @Bind(R.id.tv_im_page)
    TextView tvImPage;
    @Bind(R.id.tv_center_page)
    TextView tvCenterPage;
    @Bind(R.id.tv_find_page)
    TextView tvFindPage;
    @Bind(R.id.tv_my_page)
    TextView tvMyPage;

    @Bind(R.id.main_content)
    FrameLayout mainContent;
    //当前一级页面显示的Fragment
    private Fragment mCurrentFragment, homeFragment, imFragment, centerFragment, findFragment, myFragment;

    private FragmentManager mFragmentManager;


    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {
        super.onBaseCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @OnClick({R.id.tv_home_page, R.id.tv_im_page, R.id.tv_center_page, R.id.tv_find_page, R.id.tv_my_page})
    public void onClick(View view) {
        if (mFragmentManager == null)
            mFragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.tv_home_page:
                if (homeFragment == null)
                    homeFragment = new HomeFragment();
                mCurrentFragment = homeFragment;
                break;
            case R.id.tv_im_page:
                if (imFragment == null)
                    imFragment = new IMFragment();
                mCurrentFragment = imFragment;
                break;
            case R.id.tv_center_page:
                if (centerFragment == null)
                    centerFragment = new CenterFragment();
                mCurrentFragment = centerFragment;
                break;
            case R.id.tv_find_page:
                if (findFragment == null)
                    findFragment = new FindFragment();
                mCurrentFragment = findFragment;
                break;
            case R.id.tv_my_page:
                if (myFragment == null)
                    myFragment = new MyFragment();
                mCurrentFragment = myFragment;
                break;
        }
        FragmentUtils.switchHideFragment(mFragmentManager, R.id.main_content, mCurrentFragment, fragment);
    }
}
