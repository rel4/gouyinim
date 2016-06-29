package com.gouyin.im.center.widget;

import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.center.view.PayDynamicRedPackketView;
import com.gouyin.im.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.gouyin.com.progressdialog.ProgressDialog;

/**
 * Created by jb on 2016/6/29.
 */
public class PayDynamicRedPackketActivity extends Activity implements PayDynamicRedPackketView {
    @Bind(R.id.tv_money)
    TextView tvMoney;
    private String money;
    private String id;
    PayDynamicRedPackketPersenter persenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setRootContentView());
        ButterKnife.bind(this);
        initView();
    }


    protected View setRootContentView() {
        persenter = new PayDynamicRedPackketPersenterImpl();
        persenter.attachView(this);
        money = getIntent().getStringExtra("money");
        id = getIntent().getStringExtra("id");
        return UIUtils.inflateLayout(R.layout.activity_pay_dynamic_packet);
    }


    protected void initView() {
        SpannableString html = new SpannableString(money + "  元");
        html.setSpan(new AbsoluteSizeSpan(30, true), 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvMoney.setText(html);
    }

    @OnClick({R.id.submit, R.id.action_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                persenter.pay(id);

                break;
            case R.id.action_back:
                finish();
                break;
        }
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
        UIUtils.showToast(this, msg);
    }
    /**
     * 显示加载jindt
     */
    /**
     * 初始化加载进度条
     */
    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
    }

    private ProgressDialog progressDialog;

    protected void showProgressDialog() {
        if (progressDialog == null)
            initProgressDialog();
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    /**
     * 隐藏加载进度条
     */
    protected void hideProgressDialog() {
        if (progressDialog == null)
            return;
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
