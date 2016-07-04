package com.gouyin.im.main.model;

import com.gouyin.im.R;
import com.gouyin.im.ServerApi;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.CommentDataListBean;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.ObservableUtils;
import com.gouyin.im.utils.UIUtils;

import java.util.List;

import rx.Observable;
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

        Observable<CommentDataListBean> commentList = ServerApi.getAppAPI().getCommentList(id, page, UserInfoManager.getInstance().getAuthcode());
        ObservableUtils.parser(commentList, new ObservableUtils.Callback<CommentDataListBean>() {
            @Override
            public void onSuccess(CommentDataListBean bean) {
                if (bean != null) {
                    List<CommentDataListBean.CommentListBean> datas = bean.getData();
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
}
