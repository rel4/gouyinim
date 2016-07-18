package com.moonsister.im.my.persenter;

import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.my.view.MyFragmentView;

/**
 * Created by jb on 2016/6/27.
 */
public interface MyFragmentPresenter extends BaseIPresenter<MyFragmentView> {

    void swicth2Page(int id);

    void loadPersonHeader();

    void loadonRefreshData();

    void loadLoadMoreData();

    void uploadBackground(String path);

    void deleteDynamic(String id);
}


