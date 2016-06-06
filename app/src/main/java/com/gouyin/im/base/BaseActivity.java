package com.gouyin.im.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.utils.ConfigUtils;

import butterknife.ButterKnife;
import im.gouyin.com.progressdialog.ProgressDialog;

/**
 * Created by jb on 2016/5/30.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected View mRootView;
    private View titleView;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBaseCreate(savedInstanceState);
        mRootView = setRootContentView();
        if (mRootView == null)
            throw new RuntimeException("root layout not null");
        setContentView(mRootView);

        ButterKnife.bind(this);
        initView();
        View titleView = mRootView.findViewById(R.id.app_title_bar);
        if (titleView != null)
            initTitieBar(titleView);

    }

    private void initTitieBar(View view) {
        this.titleView = view;
        view.findViewById(R.id.action_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((TextView) view.findViewById(R.id.tv_title_name)).setText(initTitleName());

    }

    /**
     * 初始化title名字
     *
     * @return
     */
    protected String initTitleName() {
        return "标题";
    }

    /**
     * 返回titleView
     *
     * @return titleView
     */
    protected View getTitleView() {
        return titleView;
    }

    /**
     *
     *
     */
    protected abstract void initView();

    /**
     * 设置根布局
     *
     * @return 根布局
     */
    protected abstract View setRootContentView();


    @Override
    protected void onStart() {
        super.onStart();
        onBaseStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
        onBaseResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        onBasePause();
    }


    @Override
    protected void onStop() {
        super.onStop();
        onBaseStop();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        onBaseDestroy();
        ButterKnife.unbind(this);
    }


    /**
     * super.onDestroy();子类继承
     *
     * @param savedInstanceState
     */
    protected void onBaseCreate(Bundle savedInstanceState) {
    }

    /**
     * 子类继承
     */
    protected void onBaseStart() {
    }

    /**
     * 子类继承
     */
    protected void onBaseResume() {
    }

    /**
     * 子类继承
     */
    protected void onBasePause() {
    }

    /**
     * 子类继承
     */
    protected void onBaseStop() {
    }

    /**
     * 子类继承
     */
    protected void onBaseDestroy() {
    }
    /**
     * 初始化加载进度条
     */
    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
    }

    /**
     * 显示加载jindt
     */
    protected void showProgressDialog() {
        if (progressDialog == null)
            initProgressDialog();
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    /**
     * 隐藏加载进度条
     */
    protected void hideProgressDialog() {
        if (progressDialog == null)
            return;
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }


}
