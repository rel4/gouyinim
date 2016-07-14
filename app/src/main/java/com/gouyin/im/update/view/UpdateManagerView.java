package com.gouyin.im.update.view;

import com.gouyin.im.base.BaseIView;
import com.gouyin.im.bean.VersionInfo;

/**
 * Created by jb on 2016/7/14.
 */
public interface UpdateManagerView extends BaseIView {
    void onProgress(long progress, long total, boolean done);

    void downApkSuccess(VersionInfo versionInfo);
}
