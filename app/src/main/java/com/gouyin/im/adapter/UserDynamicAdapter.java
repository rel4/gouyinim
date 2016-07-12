package com.gouyin.im.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseIView;
import com.gouyin.im.base.BaseRecyclerViewAdapter;
import com.gouyin.im.base.BaseRecyclerViewHolder;
import com.gouyin.im.bean.PayRedPacketPicsBean;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.main.view.DynamicView;
import com.gouyin.im.main.widget.DynamicActivity;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.viewholder.DynamicViewHolder;

import java.util.List;

/**
 * Created by pc on 2016/6/6.
 */
public class UserDynamicAdapter extends BaseRecyclerViewAdapter<UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList> {

    private BaseIView baseIView;
    private DynamicViewHolder holder;

    public UserDynamicAdapter(List list) {
        super(list);

    }

    @Override
    protected View initRootView(ViewGroup parent, int viewType) {
        View view = UIUtils.inflateLayout(R.layout.item_home_one_menu, parent);
        holder = new DynamicViewHolder(view, viewType);
        if (view != null)
            holder.setView(baseIView);
        return holder.getRootView();
    }

    @Override
    protected BaseRecyclerViewHolder getBaseViewHolder(View v, int viewType) {
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getType();
    }

    public void updataPayData(PayRedPacketPicsBean bean) {
        if (bean == null || bean.getData() == null)
            return;
        if (datas != null) {
            for (int i = 0; i < datas.size(); i++) {
                UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList ls = datas.get(i);
                if (ls != null && StringUtis.equals(ls.getLatest_id(), bean.getData().getLatest_id())) {
                    ls.setIspay("1");
                    ls.getSimg();
                    ls.getSimg().clear();
                    ls.getSimg().addAll(bean.getData().getSimg());
                    ls.getImg().clear();
                    ls.getImg().addAll(bean.getData().getImg());
                    notifyDataSetChanged();
                }
            }
        }
    }

    public void setView(BaseIView view) {
        this.baseIView = view;
    }

    public void deleteDynamic(String id) {
        UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList bean = null;
        for (UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList data : datas) {
            if (StringUtis.equals(data.getLatest_id(), id)) {
                bean = data;
                break;
            }
        }
        if (bean != null) {
            datas.remove(bean);
            notifyDataSetChanged();

        }
    }
}
