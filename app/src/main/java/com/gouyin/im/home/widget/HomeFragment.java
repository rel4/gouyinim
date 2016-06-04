package com.gouyin.im.home.widget;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseFragment;
import com.gouyin.im.home.presenetr.HomeFragmentPresenter;
import com.gouyin.im.home.presenetr.HomeFragmentPresenterImpl;

import com.gouyin.im.home.view.HomeView;
import com.gouyin.im.utils.FragmentUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by pc on 2016/5/31.
 */
public class HomeFragment extends BaseFragment implements HomeView {

    @Bind(R.id.tv_navigation_good_select)
    TextView tvNavigationGoodSelect;
    @Bind(R.id.tv_navigation_same_city)
    TextView tvNavigationSameCity;
    @Bind(R.id.frameLyout_home_content)
    FrameLayout frameLyoutHomeContent;
    private HomeFragmentPresenter mPresenter;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment, goodSelectFragment, sameCityFragment;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = new HomeFragmentPresenterImpl(this);
        View view = inflater.inflate(R.layout.home_one_menu, container, false);
        return view;
    }


    @Override
    protected void initData() {
        onClick(tvNavigationGoodSelect);
    }


    @Override
    public void switch2GoodSelect() {
        if (goodSelectFragment == null)
            goodSelectFragment = new GoodSelectFragment();
        enterPage(goodSelectFragment);

    }

    @Override
    public void switch2SameCity() {
        if (sameCityFragment == null)
            sameCityFragment = new SameCityFragment();
        enterPage(sameCityFragment);
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
            mFragmentManager = getChildFragmentManager();

        FragmentUtils.switchHideFragment(mFragmentManager, R.id.frameLyout_home_content, mCurrentFragment, fragment);
        mCurrentFragment = fragment;
    }

    private void switchNatvigationSelect(Fragment fragment) {
        if (fragment == null)
            return;
        tvNavigationGoodSelect.setSelected(fragment == goodSelectFragment);
        tvNavigationSameCity.setSelected(fragment == sameCityFragment);
    }


    @OnClick({R.id.tv_navigation_good_select, R.id.tv_navigation_same_city})
    public void onClick(View view) {
        mPresenter.swicthNavigation(view.getId());
    }
}
