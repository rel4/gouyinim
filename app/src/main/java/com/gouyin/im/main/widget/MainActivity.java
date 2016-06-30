package com.gouyin.im.main.widget;


import android.graphics.ImageFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.base.BaseFragment;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.LoginBean;
import com.gouyin.im.bean.PayRedPacketPicsBean;
import com.gouyin.im.bean.PresonInfo;
import com.gouyin.im.bean.RongyunBean;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.find.widget.FindFragment;
import com.gouyin.im.home.widget.HomeFragment;
import com.gouyin.im.im.widget.IMHomeFragment;
import com.gouyin.im.main.model.RongyunKeyModel;
import com.gouyin.im.main.model.RongyunKeyModelImpl;
import com.gouyin.im.main.presenter.MainPresenter;
import com.gouyin.im.main.presenter.MainPresenterImpl;
import com.gouyin.im.main.view.MainView;
import com.gouyin.im.my.widget.MyFragment;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.ConfigUtils;
import com.gouyin.im.utils.FragmentUtils;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.utils.UserInfoUtils;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.FragmentEvent;

import butterknife.Bind;
import butterknife.OnClick;
import io.rong.imkit.RongyunConfig;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserData;

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
    private BaseFragment mCurrentFragment, homeFragment, imHomeFragment, findFragment, myFragment;

    private FragmentManager mFragmentManager;
    private MainPresenter mMainPresenter;

    @Override
    protected void initView() {
        mMainPresenter.switchNavigation(R.id.tv_home_page);
//        onClick(tvHomePage);
        initRxBus();
        loginRongyun();
    }


    @Override
    protected View setRootContentView() {
        mMainPresenter = new MainPresenterImpl(this);

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
        if (!UserInfoUtils.isLogin()) {
            RxBus.getInstance().send(Events.EventEnum.LOGIN, null);
            return;
        }
        if (imHomeFragment == null)
            imHomeFragment = new IMHomeFragment();
        enterPage(imHomeFragment);
    }

    @Override
    public void switch2Center() {
//        if (centerFragment == null)
//            centerFragment = new CenterFragment();
//        enterPage(centerFragment);
        if (!UserInfoUtils.isLogin()) {
            RxBus.getInstance().send(Events.EventEnum.LOGIN, null);
            return;
        }
        ActivityUtils.startDynamicSendActivity();
    }

    @Override
    public void switchFind() {
        if (findFragment == null)
            findFragment = new FindFragment();
        enterPage(findFragment);
    }

    @Override
    public void switch2My() {
        if (!UserInfoUtils.isLogin()) {
            RxBus.getInstance().send(Events.EventEnum.LOGIN, null);
            return;
        }
        if (myFragment == null)
            myFragment = new MyFragment();

        enterPage(myFragment);

    }

    /**
     * 进入
     *
     * @param fragment
     */
    private void enterPage(BaseFragment fragment) {
        if (fragment == null)
            return;
        switchNatvigationSelect(fragment);
        if (mFragmentManager == null)
            mFragmentManager = getSupportFragmentManager();

        FragmentUtils.switchHideFragment(mFragmentManager, R.id.main_content, mCurrentFragment, fragment);
        mCurrentFragment = fragment;
    }

    private void switchNatvigationSelect(BaseFragment fragment) {
        if (fragment == null)
            return;
        tvHomePage.setSelected(fragment == homeFragment);
        tvImPage.setSelected(fragment == imHomeFragment);
//        tvCenterPage.setSelected(fragment == centerFragment);
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

    /**
     *
     */
    public void loginRongyun() {
        if (!UserInfoUtils.isLogin())
            return;
        String rongyunKey = UserInfoUtils.getRongyunKey();
        if (StringUtis.isEmpty(rongyunKey))
            return;
        RongyunConfig.getInstance().connectRonyun(rongyunKey, new RongyunConfig.ConnectCallback() {

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
                RxBus.getInstance().send(Events.EventEnum.GET_RONGYUN_KEY, null);
            }
        });
    }

    /**
     * rxbus
     */
    private void initRxBus() {
        /**
         * 进入登录
         */
        RxBus.with(this)
                .setEndEvent(ActivityEvent.DESTROY)
                .setEvent(Events.EventEnum.LOGIN)
                .onNext(events -> {
                            UserInfoUtils.offline();
                            showToast(UIUtils.getStringRes(R.string.login_action));
                            ActivityUtils.startLoginMainActivity();
                        }
                )
                .create();
        /**
         * 获取融云key
         */
        RxBus.with(this)
                .setEndEvent(ActivityEvent.DESTROY)
                .setEvent(Events.EventEnum.GET_RONGYUN_KEY)
                .onNext(events -> {
                    getRongyunKey();
                })
                .create();
        /**
         * 退出进入首页
         */
        RxBus.with(this)
                .setEndEvent(ActivityEvent.DESTROY)
                .setEvent(Events.EventEnum.GO_TO_HOME)
                .onNext(events -> {
                    imHomeFragment = null;
                    findFragment = null;
                    myFragment = null;
                    switch2Home();
                })
                .create();

        RxBus.with(this)
                .setEndEvent(ActivityEvent.DESTROY)
                .setEvent(Events.EventEnum.PAY_SUCCESS_GET_DATA)
                .onNext(events -> {
                    LogUtils.e("MyFragment", "PAY_SUCCESS_GET_DATA 数据");
                    if (events != null && myFragment != null) {
                        Object message = events.message;
                        if (message instanceof PayRedPacketPicsBean) {
                            PayRedPacketPicsBean bean = (PayRedPacketPicsBean) message;
                            ((MyFragment) myFragment).updataPayData(bean);
                        }
                    }
                })
                .create();
    }

    /**
     * 获取key
     */
    public void getRongyunKey() {
        RongyunKeyModel rongyunKeyModel = new RongyunKeyModelImpl();
        rongyunKeyModel.getRongyunKey(new BaseIModel.onLoadDateSingleListener() {
            @Override
            public void onSuccess(Object o, BaseIModel.DataType dataType) {
                if (o != null && o instanceof RongyunBean) {
                    RongyunBean bean = (RongyunBean) o;
                    PresonInfo presonInfo = UserInfoUtils.getPresonInfo();
                    presonInfo.setFace(bean.getData().getFace());
                    presonInfo.setNickname(bean.getData().getNickname());
                    presonInfo.setRongyunkey(bean.getData().getToken());
                    UserInfoUtils.saveUserInfo(presonInfo);
                    loginRongyun();
                }
            }

            @Override
            public void onFailure(String msg, Throwable e) {

            }
        });

    }
}
