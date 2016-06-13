package com.gouyin.im.login.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseFragment;
import com.gouyin.im.utils.UIUtils;

/**
 * Created by pc on 2016/6/13.
 */
public class RegiterFragment extends BaseFragment {
    public static RegiterFragment newInstance(){
        return new RegiterFragment();
    }
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UIUtils.inflateLayout(R.layout.fragment_regiter,container);
    }

    @Override
    protected void initData() {

    }
}
