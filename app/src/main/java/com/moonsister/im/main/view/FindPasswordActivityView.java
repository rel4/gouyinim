package com.moonsister.im.main.view;

import com.moonsister.im.base.BaseIView;

/**
 * Created by jb on 2016/7/11.
 */
public interface FindPasswordActivityView extends BaseIView {
    void LoopMsg();

    void navigationNext(String authcode);
}
