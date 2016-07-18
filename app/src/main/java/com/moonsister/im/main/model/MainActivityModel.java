package com.moonsister.im.main.model;

import com.moonsister.im.base.BaseIModel;

/**
 * Created by jb on 2016/7/1.
 */
public interface MainActivityModel extends BaseIModel {
    void getRongyunKey(onLoadDateSingleListener listener);

    void getCertificationStatus(onLoadDateSingleListener listener);
}
