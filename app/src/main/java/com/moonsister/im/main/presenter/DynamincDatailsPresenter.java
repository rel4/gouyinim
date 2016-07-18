package com.moonsister.im.main.presenter;

import com.moonsister.im.base.BaseIPresenter;
import com.moonsister.im.main.view.DynamicDatailsView;

/**
 * Created by pc on 2016/6/8.
 */
public interface DynamincDatailsPresenter extends BaseIPresenter<DynamicDatailsView> {

    void loadCommentListData(String commentID);

    void sendComment(String id, String content, String pid);

    void deleteDynamic(String id);
}
