package com.moonsister.im.im.model;

import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.bean.FrientBaen;

/**
 * Created by jb on 2016/7/8.
 */
public interface FrientFragmentModel extends BaseIModel {
    void loadBasicData(int pageType, int page ,onLoadDateSingleListener<FrientBaen> listener);
}
