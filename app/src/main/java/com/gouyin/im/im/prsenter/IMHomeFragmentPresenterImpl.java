package com.gouyin.im.im.prsenter;

import com.gouyin.im.R;
import com.gouyin.im.im.view.IMHomeView;

/**
 * Created by jb on 2016/6/20.
 */
public class IMHomeFragmentPresenterImpl implements IMHomeFragmentPresenter {
    private IMHomeView imHomeView;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(IMHomeView imHomeView) {
        this.imHomeView = imHomeView;
    }


    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.tv_navigation_good_select:
                imHomeView.swith2PrivateChat();
                break;
            case R.id.tv_navigation_same_city:
                imHomeView.swith2Friend();
                break;
        }
    }
}
