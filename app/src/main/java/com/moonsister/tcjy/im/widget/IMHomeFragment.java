package com.moonsister.tcjy.im.widget;

import android.annotation.TargetApi;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.moonsister.tcjy.R;
import com.moonsister.tcjy.base.BaseFragment;
import com.moonsister.tcjy.base.BaseIView;
import com.moonsister.tcjy.im.prsenter.IMHomeFragmentPresenter;
import com.moonsister.tcjy.im.prsenter.IMHomeFragmentPresenterImpl;
import com.moonsister.tcjy.im.view.IMHomeView;
import com.moonsister.tcjy.utils.FragmentUtils;

import butterknife.Bind;
import butterknife.OnClick;
import io.rong.imkit.fragment.ConversationListFragment;

/**
 * Created by pc on 2016/5/31.
 */
public class IMHomeFragment extends BaseFragment implements BaseIView, IMHomeView {
    IMHomeFragmentPresenter presenter;
    @Bind(R.id.tv_navigation_good_select)
    TextView tvNavigationGoodSelect;
    @Bind(R.id.tv_navigation_same_city)
    TextView tvNavigationSameCity;
    @Bind(R.id.layout_home_content)
    FrameLayout frameLyoutHomeContent;
    private Fragment chatFragment, mCurrentFragment;
    private FrientFragment frientFragment;
    private Fragment conversationListFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        presenter = new IMHomeFragmentPresenterImpl();
        presenter.attachView(this);
        View view = inflater.inflate(R.layout.home_one_menu, container, false);
        return view;
    }

    @Override
    protected void initData() {
        tvNavigationGoodSelect.setText(resources.getString(R.string.private_hat));
        tvNavigationSameCity.setText(resources.getString(R.string.friend));
        presenter.switchNavigation(tvNavigationGoodSelect.getId());
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
    }

    @Override
    public void transfePageMsg(String msg) {
        showToast(msg);
    }


    @OnClick({R.id.tv_navigation_good_select, R.id.tv_navigation_same_city})
    public void onClick(View view) {
        ConversationListFragment instance = ConversationListFragment.getInstance();
        presenter.switchNavigation(view.getId());
    }

    @Override
    public void swith2PrivateChat() {
        //启动会话列表界面
        if (chatFragment == null)
            chatFragment = ChatFragment.newInstance();

        enterPage(chatFragment);
    }

    @Override
    public void swith2Friend() {
        if (frientFragment == null) {
            frientFragment = FrientFragment.newInstance();
            frientFragment.setPageType(FrientFragment.PAGE_MAIN);
        }
        enterPage(frientFragment);
    }

    /**
     * 进入
     *
     * @param fragment
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void enterPage(Fragment fragment) {
        if (fragment == null)
            return;
        switchNatvigationSelect(fragment);
        if (mFragmentManager == null)
            mFragmentManager = getChildFragmentManager();

        FragmentUtils.switchHideFragment(mFragmentManager, R.id.layout_home_content, mCurrentFragment, fragment);
        mCurrentFragment = fragment;
    }

    private void switchNatvigationSelect(Fragment fragment) {
        if (fragment == null)
            return;
        tvNavigationGoodSelect.setSelected(fragment == chatFragment);
        tvNavigationSameCity.setSelected(fragment == frientFragment);

    }
}
