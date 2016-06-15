package com.gouyin.im.login.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseFragment;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.login.presenter.RegiterFragmentPresener;
import com.gouyin.im.login.presenter.RegiterFragmentPresenerImpl;
import com.gouyin.im.login.view.RegiterFragmentView;
import com.gouyin.im.utils.ConfigUtils;
import com.gouyin.im.utils.FragmentUtils;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;
import com.trello.rxlifecycle.FragmentEvent;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pc on 2016/6/13.
 */
public class RegiterFragment extends BaseFragment implements RegiterFragmentView {
    @Bind(R.id.tv_security_code)
    TextView tvSecurityCode;
    @Bind(R.id.et_phone_number)
    EditText etPhoneNumber;
    @Bind(R.id.et_security_code)
    EditText etCode;
    @Bind(R.id.layout_input)
    RelativeLayout layoutInput;

    @Bind(R.id.center_line)
    ImageView centerLine;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;
    private static RegiterFragment regiterFragment;
    private RegiterFragmentPresener presener;

    public static RegiterFragment newInstance() {
        if (regiterFragment == null)
            regiterFragment = new RegiterFragment();
        return regiterFragment;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presener = new RegiterFragmentPresenerImpl();
        presener.attachView(this);
        return UIUtils.inflateLayout(R.layout.fragment_regiter, container);
    }

    @Override
    protected void initData() {
    }


    @OnClick({R.id.tv_submit, R.id.tv_security_code})
    public void onClick(View view) {
        if (view.getId() == R.id.tv_security_code) {
            String phoneNumber = etPhoneNumber.getText().toString().trim();
            if (!phoneNumber.isEmpty()) {
                currCount = 0;
                presener.getSecurityCode(phoneNumber);

            } else
                showToast(resources.getString(R.string.input_phone_number) + resources.getString(R.string.not_empty));

        } else if (view.getId() == R.id.tv_submit) {

            String code = etCode.getText().toString().trim();
            String phoneNumber = etPhoneNumber.getText().toString().trim();
            if (code == null || code.isEmpty() || phoneNumber == null || phoneNumber.isEmpty()) {
                showToast(resources.getString(R.string.input_Security_code) + resources.getString(R.string.input_phone_number) + resources.getString(R.string.not_empty));

            } else {
                presener.submitRegiter(phoneNumber, code);
            }

        }


    }

    private int currCount;

    @Override
    public void LoopMsg() {
        ConfigUtils.getInstance().getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currCount <= 60) {
                    tvSecurityCode.setClickable(false);
                    tvSecurityCode.setFocusable(false);
                    tvSecurityCode.setText((60 - currCount) + "s");
                    LoopMsg();
                    currCount++;
                } else {
                    tvSecurityCode.setText(getResources().getString(R.string.agein_get_code));
                    tvSecurityCode.setClickable(true);
                    tvSecurityCode.setFocusable(true);
                }
            }
        }, 1000);
    }


    @Override
    public void showLoading() {
        LogUtils.e(this, "showLoading");
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
    }


    @Override
    public void navigationNext(String code) {
        if (null != regiterFragment)
            FragmentUtils.switchHideFragment(getFragmentManager(), R.id.frameLyout_home_content, regiterFragment, RegiterDataFragment.newInstance());
        if (mActivity instanceof  LoginMainActivity){
            LoginMainActivity loginMainActivity = (LoginMainActivity) mActivity;
            loginMainActivity.regiterCode=code;
        }
    }

    @Override
    public void requestFailed(String reason) {
        showToast(reason);
    }
}
