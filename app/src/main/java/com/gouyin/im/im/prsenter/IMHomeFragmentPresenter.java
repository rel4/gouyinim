package com.gouyin.im.im.prsenter;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.im.view.IMHomeView;

/**
 * Created by jb on 2016/6/20.
 */
public interface IMHomeFragmentPresenter extends BaseIPresenter<IMHomeView>{
    void switchNavigation(int id);
}
