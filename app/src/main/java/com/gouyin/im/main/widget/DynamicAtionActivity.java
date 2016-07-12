package com.gouyin.im.main.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jb on 2016/7/12.
 */
public class DynamicAtionActivity extends Activity {
    @Bind(R.id.tv_delete)
    TextView tvDelete;
    @Bind(R.id.tv_up)
    TextView tvUp;
    private String id;
    private boolean isMy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_action);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
        id = intent.getStringExtra("id");
        String id1 = UserInfoManager.getInstance().getMemoryPersonInfoDetail().getId();
        isMy = StringUtis.equals(uid, id1);
        if (isMy) {
            tvDelete.setText(UIUtils.getStringRes(R.string.delete) + UIUtils.getStringRes(R.string.dynamic));
        } else {
            tvDelete.setText(UIUtils.getStringRes(R.string.report));
        }

    }

    @OnClick({R.id.tv_delete, R.id.tv_up, R.id.tv_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_delete:
                if (isMy) {
                    Events<String> events = new Events<>();
                    events.what = Events.EventEnum.DYNAMIC_ACTION;
                    events.message = id;
                    RxBus.getInstance().send(events);
                } else {
                    UIUtils.showToast(this, UIUtils.getStringRes(R.string.success));

                }
                finish();
                break;
            case R.id.tv_up:
                break;
            case R.id.tv_finish:
                finish();
                break;
        }
    }
}
