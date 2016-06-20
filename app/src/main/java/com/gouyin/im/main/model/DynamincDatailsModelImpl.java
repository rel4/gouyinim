package com.gouyin.im.main.model;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.BaseBean;

/**
 * Created by pc on 2016/6/8.
 */
public class DynamincDatailsModelImpl implements DynamincDatailsModel {

    private String TAG = this.getClass().getSimpleName();

    @Override
    public void loadData(final String fileName, final BaseIModel.onLoadDateSingleListener listener) {

        listener.onSuccess(new BaseBean(), 0);

    }
}
