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
    /**
     * type 动态类型 1红包图集，2普通图文，3普通小视频动态
     *
     * @return
     */
    public enum DynamicType {
        DEFAULT("2"), RED_PACKET("1"), video("3");


        private final String type;

        private DynamicType(String type) {
            this.type = type;
        }

        public String getValue() {
            return type;
        }
    }

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
                ActivityUtils.startRedpacketDynaimcActivity();
                break;
            case R.id.send_video:
                ActivityUtils.startTakeVideoActivity();
                break;
            case R.id.send_dynamic:
                ActivityUtils.startDefaultDynamicSendActivity(null, DynamicType.DEFAULT);
                break;
        }
    }
}
