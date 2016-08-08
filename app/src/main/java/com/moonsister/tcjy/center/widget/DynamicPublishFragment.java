package com.moonsister.tcjy.center.widget;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.moonsister.tcjy.R;
import com.moonsister.tcjy.base.BaseFragment;
import com.moonsister.tcjy.widget.MySwitch;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jb on 2016/8/8.
 */
public class DynamicPublishFragment extends BaseFragment {
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.fl_upload_content)
    FrameLayout flUploadContent;
    @Bind(R.id.ms_dynamic_charge)
    MySwitch msDynamicCharge;
    @Bind(R.id.tv_show_adress)
    TextView tvShowAdress;
    @Bind(R.id.ms_adress_show)
    MySwitch msAdressShow;
    @Bind(R.id.fl_label_content)
    FrameLayout flLabelContent;

    public static Fragment newInstance() {
        return new DynamicPublishFragment();
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dynamic_publish, container, false);
    }

    @Override
    protected void initData() {
        initLableLayout();

    }

    private void initLableLayout() {
        replaceFramgent(LableFragment.newInstance(), flLabelContent.getId());
    }


}
