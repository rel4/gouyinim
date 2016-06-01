package com.gouyin.im.main.widget;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseActivity;

import com.gouyin.im.center.widget.CenterFragment;
import com.gouyin.im.find.widget.FindFragment;
import com.gouyin.im.home.widget.HomeFragment;
import com.gouyin.im.im.widget.IMFragment;
import com.gouyin.im.main.presenter.MainPresenter;
import com.gouyin.im.main.presenter.MainPresenterImpl;
import com.gouyin.im.main.view.MainView;
import com.gouyin.im.my.widget.MyFragment;
import com.gouyin.im.utils.FragmentUtils;

import butterknife.Bind;

import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainView {
    @Bind(R.id.tv_home_page)
    TextView tvHomePage;
    @Bind(R.id.tv_im_page)
    TextView tvImPage;
    @Bind(R.id.tv_center_page)
    ImageView tvCenterPage;
    @Bind(R.id.tv_find_page)
    TextView tvFindPage;
    @Bind(R.id.tv_my_page)
    TextView tvMyPage;

    @Bind(R.id.main_content)
    FrameLayout mainContent;
    //当前一级页面显示的Fragment
    private Fragment mCurrentFragment, homeFragment, imFragment, centerFragment, findFragment, myFragment;

    private FragmentManager mFragmentManager;
    private MainPresenter mMainPresenter;

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {
        super.onBaseCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainPresenter = new MainPresenterImpl(this);
    }

    private void initPage() {
        onClick(tvHomePage);
    }

    @Override
    protected void onBaseResume() {
        initPage();
    }

    @OnClick({R.id.tv_home_page, R.id.tv_im_page, R.id.tv_center_page, R.id.tv_find_page, R.id.tv_my_page})
    public void onClick(View view) {
        mMainPresenter.switchNavigation(view.getId());
    }

    @Override
    public void switch2Home() {
        if (homeFragment == null)
            homeFragment = new HomeFragment();
        enterPage(homeFragment);
    }

    @Override
    public void switch2IM() {
        if (imFragment == null)
            imFragment = new IMFragment();
        enterPage(imFragment);
    }

    @Override
    public void switch2Center() {
        if (centerFragment == null)
            centerFragment = new CenterFragment();
        enterPage(centerFragment);
    }

    @Override
    public void switchFind() {
        if (findFragment == null)
            findFragment = new FindFragment();
        enterPage(findFragment);
    }

    @Override
    public void switch2My() {
        if (myFragment == null)
            myFragment = new MyFragment();

        enterPage(myFragment);

    }

    /**
     * 进入
     *
     * @param fragment
     */
    private void enterPage(Fragment fragment) {
        if (fragment == null)
            return;
        switchNatvigationSelect(fragment);
        if (mFragmentManager == null)
            mFragmentManager = getSupportFragmentManager();

        FragmentUtils.switchHideFragment(mFragmentManager, R.id.main_content, mCurrentFragment, fragment);
        mCurrentFragment = fragment;
    }

    private void switchNatvigationSelect(Fragment fragment) {
        if (fragment == null)
            return;
        tvHomePage.setSelected(fragment == homeFragment);
        tvImPage.setSelected(fragment == imFragment);
        tvCenterPage.setSelected(fragment == centerFragment);
        tvFindPage.setSelected(fragment == findFragment);
        tvMyPage.setSelected(fragment == myFragment);

    }
}
