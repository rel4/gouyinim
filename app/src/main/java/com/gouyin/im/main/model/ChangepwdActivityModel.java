package com.gouyin.im.main.model;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.DefaultDataBean;

/**
 * Created by jb on 2016/7/11.
 */
public interface ChangepwdActivityModel extends BaseIModel {
    void loadBasic(String oldpwd, String newpwd, onLoadDateSingleListener<DefaultDataBean> listener);
}
