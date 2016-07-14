package com.gouyin.im.update.presenter;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.update.view.UpdateManagerView;

/**
 * Created by jb on 2016/7/14.
 */
public interface UpdateManagerPresenter  extends BaseIPresenter<UpdateManagerView>{
    void loadVersionInfo(String apkPath);
}
