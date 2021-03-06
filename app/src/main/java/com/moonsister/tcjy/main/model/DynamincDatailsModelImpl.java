package com.moonsister.tcjy.main.model;

import com.moonsister.tcjy.AppConstant;
import com.moonsister.tcjy.R;
import com.moonsister.tcjy.ServerApi;
import com.moonsister.tcjy.base.BaseIModel;
import com.moonsister.tcjy.bean.CommentDataListBean;
import com.moonsister.tcjy.bean.DefaultDataBean;
import com.moonsister.tcjy.manager.UserInfoManager;
import com.moonsister.tcjy.utils.ObservableUtils;
import com.moonsister.tcjy.utils.UIUtils;

import java.util.List;

import rx.Observable;

/**
 * Created by pc on 2016/6/8.
 */
public class DynamincDatailsModelImpl implements DynamincDatailsModel {

    private String TAG = this.getClass().getSimpleName();

    @Override
    public void loadCommentListData(String id, int page, final BaseIModel.onLoadListDateListener listener) {

        Observable<CommentDataListBean> commentList = ServerApi.getAppAPI().getCommentList(id, page, UserInfoManager.getInstance().getAuthcode(), AppConstant.CHANNEL_ID);
        ObservableUtils.parser(commentList, new ObservableUtils.Callback<CommentDataListBean>() {
            @Override
            public void onSuccess(CommentDataListBean bean) {
                if (bean != null) {
                    List<CommentDataListBean.DataBean> datas = bean.getData();
                    listener.onSuccess(datas, DataType.DATA_ONE);
                } else
                    listener.onFailure(UIUtils.getStringRes(R.string.request_failed));
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }


    @Override
    public void sendComment(String id, String content, String pid, onLoadDateSingleListener listenter) {

        Observable<DefaultDataBean> observable = ServerApi.getAppAPI().getSendComment(id, content, pid, UserInfoManager.getInstance().getAuthcode(),AppConstant.CHANNEL_ID);
        ObservableUtils.parser(observable, new ObservableUtils.Callback<DefaultDataBean>() {
            @Override
            public void onSuccess(DefaultDataBean bean) {
               listenter.onSuccess(bean, DataType.DATA_ZERO);
            }

            @Override
            public void onFailure(String msg) {
               listenter.onFailure(msg);
            }
        });
    }
}
