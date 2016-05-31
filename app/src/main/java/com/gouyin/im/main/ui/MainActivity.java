package com.gouyin.im.main.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.tv_home_page)
    TextView tvHomePage;
    @Bind(R.id.tv_im_page)
    TextView tvImPage;
    @Bind(R.id.tv_center_page)
    TextView tvCenterPage;
    @Bind(R.id.tv_find_page)
    TextView tvFindPage;
    @Bind(R.id.tv_my_page)
    TextView tvMyPage;
    @Bind(R.id.main_content)
    FrameLayout mainContent;

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {
        super.onBaseCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @OnClick({R.id.tv_home_page, R.id.tv_im_page, R.id.tv_center_page, R.id.tv_find_page, R.id.tv_my_page})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_home_page:
                break;
            case R.id.tv_im_page:
                break;
            case R.id.tv_center_page:
                break;
            case R.id.tv_find_page:
                break;
            case R.id.tv_my_page:
                break;
        }
    }
}
