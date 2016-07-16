package com.gouyin.im.center.widget;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.center.presenter.PayDynamicRedPackketPersenter;
import com.gouyin.im.center.presenter.PayDynamicRedPackketPersenterImpl;
import com.gouyin.im.center.view.PayDynamicRedPackketView;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.utils.UIUtils;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.components.RxActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.gouyin.com.progressdialog.ProgressDialog;

/**
 * Created by jb on 2016/6/29.
 */
public class PayDynamicRedPackketActivity extends RxActivity implements PayDynamicRedPackketView {
    @Bind(R.id.tv_money)
    TextView tvMoney;
    private String money;
    private String id;
    PayDynamicRedPackketPersenter persenter;

    public enum RedpacketType {
        TYPE_REDPACKET(1), TYPE_FLOWER(2);
        private final int type;

        private RedpacketType(int type) {
            this.type = type;
        }

        public int getValue() {
            return type;
        }

    }

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
        SpannableString html = new SpannableString(money + " " + UIUtils.getStringRes(R.string.yuan));

        html.setSpan(new AbsoluteSizeSpan(30, true), 0, money.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvMoney.setText(html);
    }

    @OnClick({R.id.tv_aliplay_play, R.id.tv_weixin_play, R.id.action_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_aliplay_play:
                persenter.alipay(id);

                break;
            case R.id.tv_weixin_play:
                persenter.weixinPay(id);
                setRx();
                break;
            case R.id.action_back:
                finish();
                break;
        }
    }

    private void setRx() {
        RxBus.with(this)
                .setEndEvent(ActivityEvent.DESTROY)
                .setEvent(Events.EventEnum.WEIXIN_PAY_CALLBACK)
                .onNext(events -> {
                    if (events != null) {
                        Integer message = (Integer) events.message;
                        if (message == 0) {
                            persenter.getPics(id);
                        } else {
                            hideLoading();
                            transfePageMsg(UIUtils.getStringRes(R.string.pay_failure));
                        }
                    } else {
                        hideLoading();
                        transfePageMsg(UIUtils.getStringRes(R.string.pay_failure));
                    }
                })
                .create();
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

    @Override
    public void finishPage() {
        finish();
    }
}
