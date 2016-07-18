package com.moonsister.im.main.presenter;

import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.main.view.DynamicView;

/**
 * Created by pc on 2016/6/6.
 */
public interface DynamicPresenter extends BaseIPresenter<DynamicView>{
    void loadonRefreshData(String userId);

    void loadLoadMoreData(String userId);

    void switchNavigation(int id);

    void loadUserInfodetail(String uid);

    void deleteDynamic(String id);
}
