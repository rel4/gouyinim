package com.moonsister.im.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.moonsister.im.AppConstant;
import com.moonsister.im.R;
import com.moonsister.im.base.BaseIModel;
import com.moonsister.im.base.BaseRecyclerViewAdapter;
import com.moonsister.im.base.BaseRecyclerViewHolder;
import com.moonsister.im.bean.DefaultDataBean;
import com.moonsister.im.bean.SearchReasonBaen;
import com.moonsister.im.home.widget.SearchReasonActivity;
import com.moonsister.im.main.model.UserActionModelImpl;
import com.moonsister.im.utils.ActivityUtils;
import com.moonsister.im.utils.StringUtis;
import com.moonsister.im.utils.UIUtils;
import com.moonsister.im.viewholder.SearchViewHolder;

import java.util.List;

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
