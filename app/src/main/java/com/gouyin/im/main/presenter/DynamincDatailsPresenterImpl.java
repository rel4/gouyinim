package com.gouyin.im.main.presenter;


import com.gouyin.im.AppConstant;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.CommentDataListBean;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.main.model.DynamincDatailsModel;
import com.gouyin.im.main.model.DynamincDatailsModelImpl;
import com.gouyin.im.main.view.DynamicDatailsView;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

import java.util.List;

/**
 * Created by pc on 2016/6/8.
 */
public class DynamincDatailsPresenterImpl implements DynamincDatailsPresenter, BaseIModel.onLoadListDateListener<CommentDataListBean.DataBean>, BaseIModel.onLoadDateSingleListener<DefaultDataBean> {
    private DynamicDatailsView view;
    private DynamincDatailsModel dynamincDatailsModel;
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
        view.showLoading();
        dynamincDatailsModel.loadCommentListData(commentID, page, this);
        page++;
    }

    @Override
    public void sendComment(String id, String content, String pid) {
        view.showLoading();
        dynamincDatailsModel.sendComment(id, content, pid, this);
    }

    @Override
    public void onSuccess(List<CommentDataListBean.DataBean> t, BaseIModel.DataType dataType) {
        view.hideLoading();
        view.loadData(t);

    }

    @Override
    public void onSuccess(DefaultDataBean bean, BaseIModel.DataType dataType) {

        if (bean != null) {
            if (StringUtis.equals(AppConstant.code_request_success, bean.getCode())) {
                view.CommentSuccess();
            }
            view.transfePageMsg(bean.getMsg());
        } else
            view.transfePageMsg(UIUtils.getStringRes(R.string.request_failed));
        view.hideLoading();
    }

    @Override
    public void onFailure(String msg) {
        view.hideLoading();
        view.transfePageMsg(msg);
    }

}
