package com.moonsister.tcjy.home.presenetr;

import com.moonsister.tcjy.base.BaseIPresenter;
import com.moonsister.tcjy.home.view.SearchReasonActivityView;

/**
 * Created by jb on 2016/7/10.
 */
public interface SearchReasonActivityPresenter extends BaseIPresenter<SearchReasonActivityView>{
    void loadBasicData(String key);
}
