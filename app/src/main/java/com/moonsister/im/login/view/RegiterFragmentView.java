package com.moonsister.im.login.view;

import com.moonsister.im.base.BaseIView;

/**
 * Created by pc on 2016/6/14.
 */
public interface RegiterFragmentView extends BaseIView {
        void navigationNext(String code);
        void requestFailed(String reason);
        void LoopMsg();
}
