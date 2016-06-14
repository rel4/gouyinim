package com.gouyin.im.login.view;

import com.gouyin.im.base.BaseIView;

/**
 * Created by pc on 2016/6/14.
 */
public interface RegiterFragmentView extends BaseIView {
        void navigationNext();
        void requestFailed(String reason);
        void LoopMsg();
}
