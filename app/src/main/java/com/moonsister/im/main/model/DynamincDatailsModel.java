package com.moonsister.im.main.model;

import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.bean.CommentDataListBean;
import com.moonsister.im.bean.DefaultDataBean;

/**
 * Created by pc on 2016/6/8.
 */
public interface DynamincDatailsModel extends BaseIModel {
    void loadCommentListData(String id, int page, BaseIModel.onLoadListDateListener<CommentDataListBean.DataBean> listener);

    void sendComment(String id, String content, String pid, onLoadDateSingleListener<DefaultDataBean> listenter);
}
