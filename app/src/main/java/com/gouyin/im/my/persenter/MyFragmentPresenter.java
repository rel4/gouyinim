package com.gouyin.im.my.persenter;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.my.view.MyFragmentView;

/**
 * Created by jb on 2016/6/27.
 */
public interface MyFragmentPresenter  extends BaseIPresenter<MyFragmentView>{
    void loadonRefreshData(String s);

    void loadLoadMoreData(String s);

    void loadUserInfodetail(String s);

    void swicth2Page(int id);
}


