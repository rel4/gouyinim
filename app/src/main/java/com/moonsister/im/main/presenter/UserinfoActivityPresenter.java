package com.moonsister.im.main.presenter;

import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.main.view.UserinfoActivityView;

/**
 * Created by jb on 2016/7/11.
 */
public interface UserinfoActivityPresenter  extends BaseIPresenter<UserinfoActivityView>{
    void loadBasicData(String uid);
}
