package com.gouyin.im.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gouyin.im.AppConstant;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.base.BaseRecyclerViewAdapter;
import com.gouyin.im.base.BaseRecyclerViewHolder;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.bean.SearchReasonBaen;
import com.gouyin.im.home.widget.SearchReasonActivity;
import com.gouyin.im.main.model.UserActionModelImpl;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.viewholder.SearchViewHolder;
import com.gouyin.im.widget.RoundedImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jb on 2016/7/10.
 */
public class SearchAdapter extends BaseRecyclerViewAdapter<SearchReasonBaen.DataBean> {

    private SearchViewHolder holder;
    private SearchReasonActivity searchReasonActivityView;

    public SearchAdapter(List<SearchReasonBaen.DataBean> list) {
        super(list);
    }

    @Override
    protected View initRootView(ViewGroup parent, int viewType) {
        View view = UIUtils.inflateLayout(R.layout.item_search_reason, parent);
        holder = new SearchViewHolder(view);
        holder.setAdapter(this);
        return holder.getRootView();
    }

    @Override
    protected BaseRecyclerViewHolder getBaseViewHolder(View v, int viewType) {
        return holder;
    }

    public void setClick(Integer position) {
        if (datas.size() - 1 >= position) {
            searchReasonActivityView.showLoading();
            UserActionModelImpl userActionModel = new UserActionModelImpl();
            userActionModel.wacthAction(datas.get(position).getUid(), "1", new BaseIModel.onLoadDateSingleListener<DefaultDataBean>() {
                @Override
                public void onSuccess(DefaultDataBean bean, BaseIModel.DataType dataType) {
                    if (bean != null) {
                        if (StringUtis.equals(bean.getCode(), AppConstant.code_request_success)) {
                            datas.remove(datas.get(position));
                            onRefresh();
                        }
                        searchReasonActivityView.transfePageMsg(bean.getMsg());
                    } else {
                        searchReasonActivityView.transfePageMsg(UIUtils.getStringRes(R.string.request_failed));
                    }
                    searchReasonActivityView.hideLoading();
                }

                @Override
                public void onFailure(String msg) {
                    searchReasonActivityView.hideLoading();
                    searchReasonActivityView.transfePageMsg(msg);
                }
            });

        }
    }

    @Override
    public void onItemclick(View view, int position) {
        ActivityUtils.startDynamicActivity(datas.get(position).getUid());
    }

    public void setSearchReasonActivityView(SearchReasonActivity searchReasonActivityView) {
        this.searchReasonActivityView = searchReasonActivityView;
    }
}
