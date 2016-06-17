package com.gouyin.im.login.model;

import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.bean.BaseBean;

/**
 * Created by jb on 2016/6/15.
 */
public interface RegiterDataFragmentModel extends BaseIModel {
    void login(String face, String sex, String pwd, String authcode, onLoadDateListener listener);

    void upLoadIcon(String iconPath, onLoadDateListener listener);

}
