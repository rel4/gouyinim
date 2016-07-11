package com.gouyin.im.main.presenter;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.main.view.UserinfoActivityView;

/**
 * Created by jb on 2016/7/11.
 */
public interface UserinfoActivityPresenter  extends BaseIPresenter<UserinfoActivityView>{
    void loadBasicData(String uid);
}
