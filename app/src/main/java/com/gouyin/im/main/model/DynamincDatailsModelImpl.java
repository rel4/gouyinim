package com.gouyin.im.main.model;

import com.gouyin.im.R;
import com.gouyin.im.ServerApi;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.CommentDataListBean;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.UIUtils;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pc on 2016/6/8.
 */
public class DynamincDatailsModelImpl implements DynamincDatailsModel {

    private String TAG = this.getClass().getSimpleName();

    @Override
    public void loadCommentListData(String id, int page, final BaseIModel.onLoadListDateListener listener) {

        ServerApi.getAppAPI().getCommentList(id, page, UserInfoManager.getInstance().getAuthcode())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommentDataListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CommentDataListBean commentListBean) {
                        if (commentListBean != null && commentListBean.getData() != null) {
                            List<CommentDataListBean.CommentListBean> datas = commentListBean.getData();
                            listener.onSuccess(datas,DataType.DATA_ONE);
                        } else
                            listener.onFailure(UIUtils.getStringRes(R.string.request_failed));
                    }
                });

    }
}
