package com.gouyin.im.main.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseFragment;

/**
 * Created by pc on 2016/5/31.
 */
public class HomeFragment extends BaseFragment {
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("首页");
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
