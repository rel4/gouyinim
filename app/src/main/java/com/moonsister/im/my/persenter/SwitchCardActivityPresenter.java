package com.moonsister.im.my.persenter;

import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.my.view.SwitchCardActivityView;

/**
 * Created by jb on 2016/7/4.
 */
public interface SwitchCardActivityPresenter  extends BaseIPresenter<SwitchCardActivityView>{
    void loadCardInfo();
}
