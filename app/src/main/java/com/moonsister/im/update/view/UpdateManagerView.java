package com.moonsister.im.update.view;

import com.moonsister.im.base.BaseIView;
import com.moonsister.im.bean.VersionInfo;

/**
 * Created by jb on 2016/7/14.
 */
public interface UpdateManagerView extends BaseIView {
    void onProgress(long progress, long total, boolean done);

    void downApkSuccess(VersionInfo versionInfo);
}
