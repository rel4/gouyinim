package com.moonsister.im.main.presenter;

import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.main.view.MainView;

/**
 * Created by pc on 2016/6/1.
 */
public interface MainPresenter  extends BaseIPresenter<MainView>{
    void switchNavigation(int id);

    void getRongyunKey();

    void loginRongyun();

    void getCertificationStatus();
}
