package com.gouyin.im.my.widget;

import android.os.Bundle;
import android.view.View;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**约会
 *
 * Created by jb on 2016/6/27.
 */
public class AppointmentActivity extends BaseActivity {
    @Override
    protected View setRootContentView() {
        return UIUtils.inflateLayout(R.layout.activity_my_yu_yue);
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.tv_Refund)
    public void onClick() {
        ActivityUtils.startRefundActivity();
    }
}
