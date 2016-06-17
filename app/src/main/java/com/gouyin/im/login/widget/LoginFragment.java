package com.gouyin.im.login.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseFragment;
import com.gouyin.im.login.view.LoginFragmentView;
import com.gouyin.im.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pc on 2016/6/13.
 */
public class LoginFragment extends BaseFragment  implements LoginFragmentView{
    @Bind(R.id.et_phone_number)
    EditText etPhoneNumber;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @Bind(R.id.submit_login)
    TextView submitLogin;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UIUtils.inflateLayout(R.layout.fragment_login, container);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tv_forget_password, R.id.submit_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_password:
                break;
            case R.id.submit_login:
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
