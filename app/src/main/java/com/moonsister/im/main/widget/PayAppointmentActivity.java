package com.moonsister.im.main.widget;

import android.view.View;

import com.moonsister.im.R;
import com.moonsister.im.base.BaseActivity;
import com.moonsister.im.utils.ActivityUtils;
import com.moonsister.im.utils.UIUtils;

import butterknife.OnClick;

/**
 * Created by jb on 2016/7/1.
 */
public class PayAppointmentActivity extends BaseActivity {


    @Override
    protected View setRootContentView() {
        return UIUtils.inflateLayout(R.layout.activity_appointment);
    }

    @Override
    protected void initView() {

    }


    @OnClick({R.id.signed_commercial, R.id.signed, R.id.iv_asleep, R.id.tv_appointment_photo, R.id.iv_wacth_movie, R.id.iv_travel})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.signed_commercial:
                break;
            case R.id.signed:
                break;
            case R.id.iv_asleep:

                break;
            case R.id.tv_appointment_photo:
                break;
            case R.id.iv_wacth_movie:
                break;
            case R.id.iv_travel:
                break;
        }
        ActivityUtils.startPayAppointmentOrderActivity();
    }
}
