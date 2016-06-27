package com.gouyin.im.my.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseFragment;
import com.gouyin.im.utils.UIUtils;

/**
 * Created by jb on 2016/6/27.
 */
public class MyAccountTiXianFragment extends BaseFragment {
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UIUtils.inflateLayout(R.layout.fragment_my_account_recorder,container);
    }

    @Override
    protected void initData() {

    }
}
