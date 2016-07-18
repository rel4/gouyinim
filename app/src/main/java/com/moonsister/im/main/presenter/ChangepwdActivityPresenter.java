package com.moonsister.im.main.presenter;

import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.main.view.ChangepwdActivityView;

/**
 * Created by jb on 2016/7/11.
 */
public interface ChangepwdActivityPresenter extends BaseIPresenter<ChangepwdActivityView> {

    void submit(String oldpwd, String newpwd);
}
