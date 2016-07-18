package com.moonsister.im.home.presenetr;

import com.moonsister.im.R;
import com.moonsister.im.home.view.HomeView;


/**
 * Created by pc on 2016/6/1.
 */
public class HomeFragmentPresenterImpl implements HomeFragmentPresenter {
    private HomeView mHomwVie;

    public HomeFragmentPresenterImpl(HomeView homeView) {
        this.mHomwVie = homeView;
    }

    @Override
    public void swicthNavigation(int id) {
        switch (id) {
            case R.id.tv_navigation_good_select:
                mHomwVie.switch2GoodSelect();
                break;
            case R.id.tv_navigation_same_city:
                mHomwVie.switch2SameCity();
                break;
            case R.id.tv_search:
                mHomwVie.switch2Search();
                break;
        }
    }
}
