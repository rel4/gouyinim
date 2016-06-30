package com.gouyin.im.my.widget;

import android.content.Intent;
import android.view.View;
import android.widget.GridView;

import com.gouyin.im.R;
import com.gouyin.im.adapter.RZGridViewAdapter;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.center.widget.DefaultDynamicSendActivity;
import com.gouyin.im.center.widget.DynamicSendActivity;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.my.persenter.RZSecondPersenter;
import com.gouyin.im.my.persenter.RZSecondPersenterImpl;
import com.gouyin.im.my.view.RZSecondView;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;
import com.trello.rxlifecycle.ActivityEvent;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jb on 2016/6/27.
 */
public class RZSecondActivity extends BaseActivity implements RZSecondView {

    @Bind(R.id.grid_view)
    GridView gridView;
    private ArrayList<String> pics;
    private RZSecondPersenter persenter;

    @Override
    protected View setRootContentView() {
        persenter = new RZSecondPersenterImpl();
        persenter.attachView(this);
        return UIUtils.inflateLayout(R.layout.activity_rzsecond);
    }

    @Override
    protected void initView() {
        gridView.setVisibility(View.GONE);
    }


    private void setRxBus() {
        RxBus.with(this).setEndEvent(ActivityEvent.DESTROY)
                .setEvent(Events.EventEnum.GET_PHOTO_LIST)
                .onNext(events ->
                        {

                            if (events != null) {
                                Object message = events.getMessage();
                                if (message != null && message instanceof ArrayList) {
                                    pics = (ArrayList) message;
                                    gridView.setAdapter(new RZGridViewAdapter(pics));
                                    gridView.setVisibility(View.VISIBLE);

                                }
                            }
                        }
                ).create();
    }

    @OnClick({R.id.tv_add_pic, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_pic:
                ActivityUtils.startPhonePicActivity();
                setRxBus();
                break;
            case R.id.tv_submit:
                if (pics == null || pics.size() == 0) {
                    showToast(UIUtils.getStringRes(R.string.not_empty));
                    return;
                }
                Intent intent = getIntent();
                if (intent == null)
                    return;

                String address = intent.getStringExtra("address");
                String height = intent.getStringExtra("height");
                String sex = intent.getStringExtra("sex");
                String nike = intent.getStringExtra("nike");
                String avaterpath = intent.getStringExtra("path");
                String sexid = null;
                if (StringUtis.equals(UIUtils.getStringRes(R.string.boy), sex)) {
                    sexid = "1";
                } else {
                    sexid = "2";
                }
                String[] splits = null;
                if (address.contains(".")) {
                    splits = address.split("\\.");
                }
                String address1 = "";
                String address2 = "";
                if (splits != null) {
                    if (splits.length >= 2) {
                        address1 = splits[0];
                        address2 = splits[1];
                    }
                }
                persenter.submit(address1, address2, height, sexid, nike, avaterpath, pics);

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
    public void success() {
        ActivityUtils.startRZThidActivity();
        finish();
    }
}
