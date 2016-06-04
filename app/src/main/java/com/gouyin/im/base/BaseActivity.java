package com.gouyin.im.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by jb on 2016/5/30.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBaseCreate(savedInstanceState);
        setContentView(setRootContentView());
        ButterKnife.bind(this);
        initView();

    }

    /**
     *
     *
     */
    protected abstract void initView();

    /**
     * 设置根布局
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


}
