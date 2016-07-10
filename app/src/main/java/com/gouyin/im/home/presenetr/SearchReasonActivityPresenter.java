package com.gouyin.im.home.presenetr;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.home.view.SearchReasonActivityView;

/**
 * Created by jb on 2016/7/10.
 */
public interface SearchReasonActivityPresenter extends BaseIPresenter<SearchReasonActivityView>{
    void loadBasicData(String key);
}
