package com.gouyin.im.main.model;

import com.gouyin.im.base.BaseIModel;

/**
 * Created by pc on 2016/6/8.
 */
public interface DynamincDatailsModel {
    void loadData(String fileName,BaseIModel.onLoadDateListener listener);
}
