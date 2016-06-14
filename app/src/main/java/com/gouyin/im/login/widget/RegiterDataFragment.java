package com.gouyin.im.login.widget;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseFragment;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pc on 2016/6/13.
 */
public class RegiterDataFragment extends BaseFragment {
    @Bind(R.id.add_icom)
    ImageView addIcom;
    @Bind(R.id.tv_boy)
    TextView tvBoy;
    @Bind(R.id.girls)
    TextView girls;
    @Bind(R.id.tv_pwd)
    TextView tvPwd;
    @Bind(R.id.edit_password)
    EditText editPassword;

    public static RegiterDataFragment newInstance() {
        return new RegiterDataFragment();
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UIUtils.inflateLayout(R.layout.fragment_regiter_data, container);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.add_icom, R.id.tv_boy, R.id.girls, R.id.id_tv_load})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_icom:
                UIUtils.startActivity(SelectPicPopupActivity.class);
                startActivityForResult(new Intent(getActivity(),SelectPicPopupActivity.class), 1);
                break;
            case R.id.tv_boy:
                break;
            case R.id.girls:
                break;
            case R.id.id_tv_load:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.e(this,"resultCode : " +requestCode);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
