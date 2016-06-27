package com.gouyin.im.my.model;

import com.gouyin.im.base.BaseIModel;

/**
 * Created by jb on 2016/6/27.
 */
public interface MyFragmentModel extends BaseIModel {
    void loadPersonHeader();

    void loadonRefreshData(int page,onLoadListDateListener listener);
}
