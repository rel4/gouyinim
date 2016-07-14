package com.gouyin.im.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.gouyin.im.AppConstant;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.base.BaseRecyclerViewAdapter;
import com.gouyin.im.base.BaseRecyclerViewHolder;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.bean.FrientBaen;
import com.gouyin.im.main.model.UserActionModelImpl;
import com.gouyin.im.my.view.FrientFragmentView;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.viewholder.FrientViewHoler;

import java.util.IdentityHashMap;
import java.util.List;

/**
 * Created by jb on 2016/7/8.
 */
public class FrientAdapter extends BaseRecyclerViewAdapter<FrientBaen.DataBean> {

    private FrientViewHoler viewHoler;
    private FrientFragmentView view;

    public FrientAdapter(List<FrientBaen.DataBean> list, FrientFragmentView view) {
        super(list);
        this.view = view;
    }

    @Override
    protected View initRootView(ViewGroup parent, int viewType) {
        View view = UIUtils.inflateLayout(R.layout.item_frient);
        viewHoler = new FrientViewHoler(view);
        viewHoler.setAdapter(this);
        return viewHoler.getRootView();
    }

    @Override
    protected BaseRecyclerViewHolder getBaseViewHolder(View v, int viewType) {

        return viewHoler;
    }

    @Override
    public void onItemclick(View view, int position) {
        if (datas != null && datas.get(position) != null) {
            FrientBaen.DataBean dataBean = datas.get(position);
            if (!StringUtis.isEmpty(dataBean.getUid())) {
                ActivityUtils.startDynamicActivity(dataBean.getUid());
            }

        }
    }

    public void setClick(Integer position) {
        if (datas.size() - 1 >= position) {
            view.showLoading();
            UserActionModelImpl userActionModel = new UserActionModelImpl();
            userActionModel.wacthAction(datas.get(position).getUid(), "2", new BaseIModel.onLoadDateSingleListener<DefaultDataBean>() {
                @Override
                public void onSuccess(DefaultDataBean bean, BaseIModel.DataType dataType) {
                    if (bean != null) {
                        if (StringUtis.equals(bean.getCode(), AppConstant.code_request_success)) {
                            datas.remove(datas.get(position));
                            onRefresh();
                        }
                        view.transfePageMsg(bean.getMsg());
                    } else {
                        view.transfePageMsg(UIUtils.getStringRes(R.string.request_failed));
                    }
                    view.hideLoading();
                }

                @Override
                public void onFailure(String msg) {
                    view.hideLoading();
                    view.transfePageMsg(msg);
                }
            });

        }
    }
}
