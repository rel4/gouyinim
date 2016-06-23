package com.gouyin.im.center.widget;

import android.view.View;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.UIUtils;

import butterknife.OnClick;

/**
 * Created by jb on 2016/6/23.
 */
public class DynamicSendActivity extends BaseActivity {
    @Override
    protected View setRootContentView() {
        View view = UIUtils.inflateLayout(R.layout.activity_dynamic_send);
        return view;
    }

    @Override
    protected void initView() {

    }


    @OnClick({R.id.send_red_packet_list, R.id.send_video, R.id.send_dynamic})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_red_packet_list:
                break;
            case R.id.send_video:
                break;
            case R.id.send_dynamic:
                ActivityUtils.startDefaultDynamicSendActivity();
                break;
        }
    }
}
