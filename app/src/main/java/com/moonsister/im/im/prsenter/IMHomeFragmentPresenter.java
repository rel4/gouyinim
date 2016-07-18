package com.moonsister.im.im.prsenter;

import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.im.view.IMHomeView;

/**
 * Created by jb on 2016/6/20.
 */
public interface IMHomeFragmentPresenter extends BaseIPresenter<IMHomeView>{
    void switchNavigation(int id);
}
