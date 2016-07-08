package com.gouyin.im.im.prsenter;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.my.view.FrientFragmentView;

/**
 * Created by jb on 2016/7/8.
 */
public interface FrientFragmentPresenter extends BaseIPresenter<FrientFragmentView> {
    void loadBasicData(int pageType);

    void loadRefresh(int pageType);
}
