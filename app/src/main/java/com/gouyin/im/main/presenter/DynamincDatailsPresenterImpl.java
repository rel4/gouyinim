package com.gouyin.im.main.presenter;


import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.BaseDataBean;
import com.gouyin.im.bean.CommentDataListBean;
import com.gouyin.im.main.model.DynamincDatailsModelImpl;
import com.gouyin.im.main.view.DynamicDatailsView;

import java.util.List;

/**
 * Created by pc on 2016/6/8.
 */
public class DynamincDatailsPresenterImpl implements DynamincDatailsPresenter, BaseIModel.onLoadListDateListener<CommentDataListBean.CommentListBean> {
    private DynamicDatailsView view;
    private DynamincDatailsModelImpl dynamincDatailsModel;
    private int page = 1;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(DynamicDatailsView dynamicDatailsView) {
        this.view = dynamicDatailsView;
        dynamincDatailsModel = new DynamincDatailsModelImpl();
    }

    @Override
    public void loadCommentListData(String commentID) {
        dynamincDatailsModel.loadCommentListData(commentID, page, this);
    }

    @Override
    public void onSuccess(List<CommentDataListBean.CommentListBean> t, BaseIModel.DataType dataType) {
        view.loadData(t);

    }

    @Override
    public void onFailure(String msg) {

    }

}
