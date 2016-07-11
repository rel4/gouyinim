package com.gouyin.im.main.presenter;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.main.view.ChangepwdActivityView;

/**
 * Created by jb on 2016/7/11.
 */
public interface ChangepwdActivityPresenter extends BaseIPresenter<ChangepwdActivityView> {

    void submit(String oldpwd, String newpwd);
}
