package com.moonsister.im.login.presenter;

import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.login.view.RegiterDataFragmentView;

/**
 * Created by jb on 2016/6/15.
 */
public interface RegiterDataFragmentPresenter extends BaseIPresenter<RegiterDataFragmentView>{
    void  login(String face,String sex,String pwd,String authcode);
    void  upLoadIcon(String iconPath);
}
