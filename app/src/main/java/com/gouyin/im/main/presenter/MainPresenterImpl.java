package com.gouyin.im.main.presenter;

import com.gouyin.im.R;
import com.gouyin.im.main.view.MainView;

/**
 * Created by pc on 2016/6/1.
 */
public class MainPresenterImpl implements MainPresenter {
    private final MainView mMainView;

    public MainPresenterImpl(MainView mainView) {
        this.mMainView = mainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.tv_home_page:
                mMainView.switch2Home();
                break;
            case R.id.tv_im_page:
                mMainView.switch2IM();
                break;
            case R.id.tv_center_page:
                mMainView.switch2Center();
                break;
            case R.id.tv_find_page:
                mMainView.switchFind();
                break;
            case R.id.tv_my_page:
                mMainView.switch2My();
                break;
        }
    }
}
