package com.gouyin.im.login.model;

import com.gouyin.im.base.BaseIModel;

/**
 * Created by jb on 2016/6/15.
 */
public interface RegiterDataFragmentModel extends BaseIModel {
    void login(String face, String sex, String pwd, String authcode, onLoadDateSingleListener listener);

    void upLoadIcon(String iconPath, onLoadDateSingleListener listener);

}
