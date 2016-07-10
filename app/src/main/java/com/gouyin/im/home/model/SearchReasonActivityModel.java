package com.gouyin.im.home.model;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.SearchReasonBaen;

/**
 * Created by jb on 2016/7/10.
 */
public interface SearchReasonActivityModel extends BaseIModel{
    void loadBasicData(String key, int page, onLoadDateSingleListener<SearchReasonBaen> listener);
}
