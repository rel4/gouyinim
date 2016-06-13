package com.gouyin.im.login.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.login.presenter.LoginMainPresenter;
import com.gouyin.im.login.presenter.LoginMainPresenterImpl;
import com.gouyin.im.login.widget.LoginMainView;
import com.gouyin.im.utils.FragmentUtils;
import com.gouyin.im.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pc on 2016/6/13.
 */
public class LoginMainActivity extends BaseActivity implements LoginMainView {
    @Bind(R.id.frameLyout_home_content)
    FrameLayout frameLyoutHomeContent;
    @Bind(R.id.tv_navigation_login)
    TextView tv_navigation_login;
    @Bind(R.id.tv_navigation_register)
    TextView tv_navigation_register;
    private LoginMainPresenter presenter;
    private FragmentManager fragmentManager;
    @Override
    protected void initView() {
        fragmentManager = getSupportFragmentManager();
        presenter.swicthNavigation(R.id.tv_navigation_login);
    }

    @Override
    protected View setRootContentView() {
        presenter = new LoginMainPresenterImpl(this);
        return UIUtils.inflateLayout(R.layout.activity_login_main);
    }
    private Fragment currentFragment,loginFragment,regiterFragment;
    @Override
    public void swicth2Login() {
        if (loginFragment==null) {
           loginFragment = LoginFragment.newInstance();
        }
        switchNatvigationSelect(loginFragment);
        FragmentUtils.switchHideFragment(fragmentManager,R.id.frameLyout_home_content,currentFragment,loginFragment);
    }

    @Override
    public void swicth2Register() {
        if (regiterFragment==null) {
            regiterFragment = RegiterFragment.newInstance();
        }
        switchNatvigationSelect(regiterFragment);
        FragmentUtils.switchHideFragment(fragmentManager,R.id.frameLyout_home_content,currentFragment,regiterFragment);
    }


    @OnClick({R.id.tv_navigation_login, R.id.tv_navigation_register})
    public void onClick(View view) {
        presenter.swicthNavigation(view.getId());
    }

    private void switchNatvigationSelect(Fragment fragment) {
        if (fragment == null)
            return;
        tv_navigation_login.setSelected(fragment == loginFragment);
        tv_navigation_register.setSelected(fragment == regiterFragment);
    }
}
