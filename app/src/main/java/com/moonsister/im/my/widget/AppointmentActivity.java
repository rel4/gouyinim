package com.moonsister.im.my.widget;

import android.view.View;

import com.moonsister.im.R;
import com.moonsister.im.base.BaseActivity;
import com.moonsister.im.utils.ActivityUtils;
import com.moonsister.im.utils.UIUtils;

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
