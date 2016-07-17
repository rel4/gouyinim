package com.gouyin.im.find.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseFragment;
import com.gouyin.im.utils.UIUtils;

/**
 * Created by pc on 2016/5/31.
 */
public class FindFragment extends BaseFragment {

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return UIUtils.inflateLayout(R.layout.fragment_find, container);
    }

    @Override
    protected void initData() {
    }


}
