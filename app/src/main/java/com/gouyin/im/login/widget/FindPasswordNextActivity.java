package com.gouyin.im.login.widget;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gouyin.im.CacheManager;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.login.presenter.FindPasswordNextActivityPresenter;
import com.gouyin.im.login.presenter.FindPasswordNextActivityPresenterImpl;
import com.gouyin.im.login.view.FindPasswordNextActivityView;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jb on 2016/7/11.
 */
public class FindPasswordNextActivity extends BaseActivity implements FindPasswordNextActivityView {
    @Bind(R.id.et_newpwd)
    EditText etNewpwd;
    @Bind(R.id.et_input_password)
    EditText etInputPassword;
    private FindPasswordNextActivityPresenter presenter;
    private String code;

    @Override
    protected View setRootContentView() {
        code = getIntent().getStringExtra("code");
        return UIUtils.inflateLayout(R.layout.activity_find_password_next);
    }

    @Override
    protected void initView() {
        presenter = new FindPasswordNextActivityPresenterImpl();
        presenter.attachView(this);
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


    @OnClick(R.id.tv_submit)
    public void onClick() {
        String input = etInputPassword.getText().toString();
        String newpwd = etNewpwd.getText().toString();
        if (StringUtis.isEmpty(input)) {
            showToast(UIUtils.getStringRes(R.string.again_input) + UIUtils.getStringRes(R.string.not_empty));
            return;
        }
        if (StringUtis.isEmpty(newpwd)) {
            showToast(UIUtils.getStringRes(R.string.new_password) + UIUtils.getStringRes(R.string.not_empty));
            return;
        }
        if (!StringUtis.equals(input, newpwd)) {
            showToast(UIUtils.getStringRes(R.string.new_password) + UIUtils.getStringRes(R.string.again_input) + UIUtils.getStringRes(R.string.not_same));
            return;
        }
        presenter.submit(newpwd, code);
    }

    @Override
    public void pageFinish() {
        finish();
    }
}
