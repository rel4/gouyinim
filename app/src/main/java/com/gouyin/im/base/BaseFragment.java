package com.gouyin.im.base;


import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gouyin.im.utils.ConfigUtils;
import com.gouyin.im.utils.UIUtils;

import butterknife.ButterKnife;
import im.gouyin.com.progressdialog.ProgressDialog;

/**
 * Created by pc on 2016/5/31.
 */
public abstract class BaseFragment extends BaseRxFragment {
    private View mRootView;
    private ProgressDialog progressDialog;
    protected Resources resources;
    protected Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        resources = getResources();
        mActivity = getActivity();
        mRootView = onBaseCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, mRootView);
        initData();
        return mRootView;

    }

    protected void showToast(String msg) {
        UIUtils.showToast(getActivity(), msg);
    }


    protected View onBaseCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = initView(inflater, container, savedInstanceState);
        return mView;
    }

    /**
     * fragment 布局
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    public void onStart() {
        super.onStart();
        onBaseStart();
    }

    protected void onBaseStart() {
    }

    @Override
    public void onPause() {
        super.onPause();
        onBasePause();
    }

    protected void onBasePause() {
    }

    @Override
    public void onResume() {
        super.onResume();
        onBaseResume();

    }


    protected void onBaseResume() {
    }

    @Override
    public void onStop() {
        super.onStop();
        onBaseStop();
    }

    protected void onBaseStop() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onBaseDestroy();
    }

    protected void onBaseDestroy() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 初始化加载进度条
     */
    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getActivity()==null?ConfigUtils.getInstance().getActivityContext():getActivity());
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
