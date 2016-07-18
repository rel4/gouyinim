package com.moonsister.im.login.presenter;

import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.login.view.FindPasswordNextActivityView;

/**
 * Created by jb on 2016/7/11.
 */
public interface FindPasswordNextActivityPresenter extends BaseIPresenter<FindPasswordNextActivityView> {
    void submit(String newpwd, String code);
}
