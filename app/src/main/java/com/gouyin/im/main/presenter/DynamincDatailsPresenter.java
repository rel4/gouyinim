package com.gouyin.im.main.presenter;

import com.gouyin.im.base.BaseIPresenter;
import com.gouyin.im.main.view.DynamicDatailsView;

/**
 * Created by pc on 2016/6/8.
 */
public interface DynamincDatailsPresenter extends BaseIPresenter<DynamicDatailsView> {

    void loadCommentListData(String commentID);
}
