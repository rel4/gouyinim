package com.gouyin.im.im.model;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.FrientBaen;

/**
 * Created by jb on 2016/7/8.
 */
public interface FrientFragmentModel extends BaseIModel {
    void loadBasicData(int pageType, int page ,onLoadDateSingleListener<FrientBaen> listener);
}
