package com.gouyin.im.main.view;

import com.gouyin.im.base.BaseIView;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.BaseDataBean;
import com.gouyin.im.bean.CommentDataListBean;

import java.util.List;

/**
 * Created by jb on 2016/6/8.
 */
public interface DynamicDatailsView extends BaseIView {
    void loadData(List<CommentDataListBean.DataBean> datas);

    void CommentSuccess();

    void deleteDynamic(String id);
}
