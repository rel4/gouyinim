package com.moonsister.im.main.view;

import com.moonsister.im.base.BaseIView;
import com.moonsister.im.bean.CommentDataListBean;

import java.util.List;

/**
 * Created by jb on 2016/6/8.
 */
public interface DynamicDatailsView extends BaseIView {
    void loadData(List<CommentDataListBean.DataBean> datas);

    void CommentSuccess();

    void deleteDynamic(String id);
}
