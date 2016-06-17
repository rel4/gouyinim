package com.gouyin.im.login.view;

import com.gouyin.im.base.BaseIView;

/**
 * Created by jb on 2016/6/15.
 */
public interface RegiterDataFragmentView  extends BaseIView{
    void navigationNext();
    void requestFailed(String reason);

    void uploadSuccess(String path);
}
