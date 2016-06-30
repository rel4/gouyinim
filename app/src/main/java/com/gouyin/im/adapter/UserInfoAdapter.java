package com.gouyin.im.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseRecyclerViewAdapter;
import com.gouyin.im.base.BaseRecyclerViewHolder;
import com.gouyin.im.bean.PayRedPacketPicsBean;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.center.widget.DynamicSendActivity;
import com.gouyin.im.main.widget.DynamicDatailsActivity;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.viewholder.UserInfoViewHolder;

import java.util.List;

/**
 * Created by pc on 2016/6/6.
 */
public class UserInfoAdapter extends BaseRecyclerViewAdapter<UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList> {
    private UserInfoViewHolder tvViewHolder;
    private UserInfoViewHolder picViewHolder;

    public UserInfoAdapter(List list) {
        super(list);

    }

    @Override
    protected View initRootView(ViewGroup parent, int viewType) {
        View view = UIUtils.inflateLayout(R.layout.item_home_one_menu, parent);

        return view;
    }

    @Override
    protected BaseRecyclerViewHolder getBaseViewHolder(View v, int viewType) {
        return new UserInfoViewHolder(v, viewType);
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
}
