package com.moonsister.im.home.presenetr;

import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.home.view.SearchReasonActivityView;

/**
 * Created by jb on 2016/7/10.
 */
public interface SearchReasonActivityPresenter extends BaseIPresenter<SearchReasonActivityView>{
    void loadBasicData(String key);
}
