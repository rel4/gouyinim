package com.gouyin.im.login.presenter;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.login.view.RegiterDataFragmentView;

/**
 * Created by jb on 2016/6/15.
 */
public interface RegiterDataFragmentPresenter extends BaseIPresenter<RegiterDataFragmentView>{
    void  login(String face,String sex,String pwd,String authcode);
}
