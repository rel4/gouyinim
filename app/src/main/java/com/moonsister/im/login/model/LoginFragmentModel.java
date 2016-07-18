package com.moonsister.im.login.model;

import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.bean.LoginBean;

/**
 * Created by jb on 2016/6/17.
 */
public interface LoginFragmentModel  extends BaseIModel{
    void login(String phone, String password,onLoadDateSingleListener<LoginBean> Listener);

}
