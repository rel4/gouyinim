package com.gouyin.im.main.widget;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.view.KeyEvent;
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
import com.gouyin.im.rongyun.widget.AppConversationActivity;
import com.gouyin.im.utils.ConfigUtils;
import com.gouyin.im.utils.FragmentUtils;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;
import io.rong.imkit.RongLogUtils;
import io.rong.imkit.RongyunConfig;
import io.rong.imlib.RongIMClient;

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
    protected void initView() {
        mMainPresenter.switchNavigation(R.id.tv_home_page);
//        onClick(tvHomePage);

    }


    @Override
    protected View setRootContentView() {
        mMainPresenter = new MainPresenterImpl(this);
        RongyunConfig.getInstance().connectRonyun("szr8DvbkpxjhVYgwTJBOy14E80Qlu5sSomiFVkhRoX6fvIUQ2yA3o8Dw8WNWCRbBKAxe9Aln5eE=", new RongyunConfig.ConnectCallback() {

            @Override
            public void onSuccess(String s) {
                LogUtils.e(MainActivity.class, "onSuccess : " + s);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtils.e(MainActivity.class, "onTokenIncorrect Message : " + errorCode.getMessage());
                LogUtils.e(MainActivity.class, "onTokenIncorrect name : " + errorCode.name());
                LogUtils.e(MainActivity.class, "onTokenIncorrect Value : " + errorCode.getValue());
            }

            @Override
            public void onTokenIncorrect() {
                LogUtils.e(MainActivity.class, "onTokenIncorrect : ");
            }
        });
        return UIUtils.inflateLayout(R.layout.activity_main);
    }

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {
        super.onBaseCreate(savedInstanceState);
        ConfigUtils.getInstance().setActivityContext(this);
    }

    @OnClick({R.id.tv_home_page, R.id.tv_im_page, R.id.tv_center_page, R.id.tv_find_page, R.id.tv_my_page})
    public void onClick(View view) {

        mMainPresenter.switchNavigation(
                view.getId());
    }

    @Override
    public void switch2Home() {
        if (homeFragment == null)
            homeFragment = new HomeFragment();
        enterPage(homeFragment);
    }

    @Override
    public void switch2IM() {
        Intent intent = new Intent(this, AppConversationActivity.class);
        intent.putExtra("targetId","11");
        UIUtils.startActivity(intent);

//        if (imFragment == null)
//            imFragment = new IMFragment();
//        enterPage(imFragment);
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
            mFragmentManager = getFragmentManager();

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

    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            // 判断是否在两秒之内连续点击返回键，是则退出，否则不退出
            if (System.currentTimeMillis() - exitTime > 2000) {
                showToast("再按一次退出程序");
                // 将系统当前的时间赋值给exitTime
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
