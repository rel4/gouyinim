package com.gouyin.im.login.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseFragment;
import com.gouyin.im.login.presenter.LoginFragmentPersenter;
import com.gouyin.im.login.presenter.LoginFragmentPersenterImpl;
import com.gouyin.im.login.view.LoginFragmentView;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pc on 2016/6/13.
 */
public class LoginFragment extends BaseFragment implements LoginFragmentView {
    @Bind(R.id.et_phone_number)
    EditText etPhoneNumber;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @Bind(R.id.submit_login)
    TextView submitLogin;
    private LoginFragmentPersenter persenter;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        persenter = new LoginFragmentPersenterImpl();
        persenter.attachView(this);
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
                login();
                break;
        }
    }

    private void login() {
        String password = etPassword.getText().toString().trim();
        String phone = etPhoneNumber.getText().toString().trim();
        if (StringUtis.isEmpty(password)) {
            showToast(resources.getString(R.string.input_password) + resources.getString(R.string.not_empty));
            return;
        }
        if (StringUtis.isEmpty(phone)) {
            showToast(resources.getString(R.string.input_phone_number) + resources.getString(R.string.not_empty));
            return;
        }
        persenter.loginSubmit(phone, password);

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

    @Override
    public void loginSuccss() {
        showToast(resources.getString(R.string.str_login) + resources.getString(R.string.success));
        UIUtils.sendDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().finish();
            }
        }, 1000);
    }
}
