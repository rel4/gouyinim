package com.gouyin.im.login.model;

import com.gouyin.im.base.BaseIModel;

/**
 * Created by jb on 2016/7/11.
 */
public interface FindPasswordNextActivityModel extends BaseIModel {
    void submit(String newpwd, String code, onLoadDateSingleListener listenter);
}
