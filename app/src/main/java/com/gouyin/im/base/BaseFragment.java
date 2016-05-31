package com.gouyin.im.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pc on 2016/5/31.
 */
public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return onBaseCreateView(inflater, container, savedInstanceState);

    }

    protected View onBaseCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View mView = initView(inflater, container, savedInstanceState);
        return mView;
    }

    /**
     * fragment 布局
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


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
}
