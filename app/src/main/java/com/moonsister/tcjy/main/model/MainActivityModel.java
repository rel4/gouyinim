package com.moonsister.tcjy.main.model;

import com.moonsister.tcjy.base.BaseIModel;

/**
 * Created by jb on 2016/7/1.
 */
public interface MainActivityModel extends BaseIModel {
    void getRongyunKey(onLoadDateSingleListener listener);

    void getCertificationStatus(onLoadDateSingleListener listener);
}
