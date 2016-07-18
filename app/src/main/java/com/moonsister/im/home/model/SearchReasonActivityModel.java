package com.moonsister.im.home.model;

import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.bean.SearchReasonBaen;

/**
 * Created by jb on 2016/7/10.
 */
public interface SearchReasonActivityModel extends BaseIModel{
    void loadBasicData(String key, int page, onLoadDateSingleListener<SearchReasonBaen> listener);
}
