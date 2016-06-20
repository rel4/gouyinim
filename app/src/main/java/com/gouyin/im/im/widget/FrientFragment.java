package com.gouyin.im.im.widget;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gouyin.im.base.BaseFragment;

/**
 * Created by jb on 2016/6/20.
 */
public class FrientFragment extends BaseFragment {
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(this.getClass().getSimpleName());
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    protected void initData() {

    }

    public static Fragment newInstance() {
        return new FrientFragment();
    }
}
