package com.moonsister.im.im.prsenter;

import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.my.view.FrientFragmentView;

/**
 * Created by jb on 2016/7/8.
 */
public interface FrientFragmentPresenter extends BaseIPresenter<FrientFragmentView> {
    void loadBasicData(int pageType);

    void loadRefresh(int pageType);
}
