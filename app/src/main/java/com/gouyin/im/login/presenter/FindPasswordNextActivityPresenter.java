package com.gouyin.im.login.presenter;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.login.view.FindPasswordNextActivityView;

/**
 * Created by jb on 2016/7/11.
 */
public interface FindPasswordNextActivityPresenter extends BaseIPresenter<FindPasswordNextActivityView> {
    void submit(String newpwd, String code);
}
