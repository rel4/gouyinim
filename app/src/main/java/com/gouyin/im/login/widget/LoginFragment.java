package com.gouyin.im.login.widget;

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
public class LoginFragment extends BaseFragment {
    public static LoginFragment  newInstance(){
        return new LoginFragment();
    }
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UIUtils.inflateLayout(R.layout.fragment_login,container);
    }

    @Override
    protected void initData() {

    }
}
