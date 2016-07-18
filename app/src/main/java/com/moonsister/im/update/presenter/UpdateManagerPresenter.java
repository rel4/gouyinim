package com.moonsister.im.update.presenter;

import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.update.view.UpdateManagerView;

/**
 * Created by jb on 2016/7/14.
 */
public interface UpdateManagerPresenter  extends BaseIPresenter<UpdateManagerView>{
    void loadVersionInfo(String apkPath);
}
