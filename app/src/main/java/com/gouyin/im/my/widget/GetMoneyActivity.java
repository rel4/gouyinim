package com.gouyin.im.my.widget;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gouyin.im.ImageServerApi;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.bean.GetMoneyBean;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.my.persenter.GetMoneyPersenter;
import com.gouyin.im.my.persenter.GetMoneyPersenterImpl;
import com.gouyin.im.my.view.GetMoneyView;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.UIUtils;
import com.trello.rxlifecycle.ActivityEvent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提现到银行卡
 * Created by jb on 2016/6/27.
 */
public class GetMoneyActivity extends BaseActivity implements GetMoneyView {
    @Bind(R.id.iv_bank_logo)
    ImageView ivBankLogo;
    @Bind(R.id.tv_bank_name)
    TextView tvBankName;
    @Bind(R.id.tv_bank_number)
    TextView tvBankNumber;
    @Bind(R.id.et_input_money)
    EditText etInputMoney;
    private GetMoneyPersenter persenter;

    @Override
    protected View setRootContentView() {
        persenter = new GetMoneyPersenterImpl();
        persenter.attachView(this);
        return UIUtils.inflateLayout(R.layout.activity_ti_xian);
    }

    @Override
    protected void initView() {
        persenter.loadbasicInfo();
    }


    @OnClick({R.id.layout_swicth_card, R.id.tv_add_alipay, R.id.tv_add_bank_card, R.id.tv_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_swicth_card:
                break;
            case R.id.tv_add_alipay:
                //type卡类型 1银行卡2支付宝3微信
                ActivityUtils.startAddCardActivity(UIUtils.getStringRes(R.string.alipay), "2");
                break;
            case R.id.tv_add_bank_card:
                break;
            case R.id.tv_sure:
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
        showToast(msg);
    }

    @Override
    public void setBasicInfo(GetMoneyBean getMoneyBean) {
        GetMoneyBean.DataBean data = getMoneyBean.getData();
        tvBankName.setText(data.getBank_name());
        tvBankNumber.setText(data.getBank_no());
        ImageServerApi.showURLSamllImage(ivBankLogo, data.getLogo());
    }

    public void seRx() {
        RxBus.with(this)
                .setEndEvent(ActivityEvent.DESTROY)
                .setEvent(Events.EventEnum.CARD_CHANGE)
                .onNext(events -> {
                    persenter.loadbasicInfo();
                })
                .create();
    }
}
