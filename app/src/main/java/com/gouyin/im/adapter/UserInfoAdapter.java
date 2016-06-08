package com.gouyin.im.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseRecyclerViewAdapter;
import com.gouyin.im.base.BaseRecyclerViewHolder;
import com.gouyin.im.bean.UserInfoBean;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.viewholder.UserInfoViewHolder;

import java.util.List;

/**
 * Created by pc on 2016/6/6.
 */
public class UserInfoAdapter extends BaseRecyclerViewAdapter <UserInfoBean>{
    private UserInfoViewHolder tvViewHolder;
    private UserInfoViewHolder picViewHolder;

    public UserInfoAdapter(List list) {
        super(list);

    }

    @Override
    protected View initRootView(ViewGroup parent, int viewType) {
        View view = UIUtils.inflateLayout(R.layout.item_home_one_menu,parent);

        return view;
    }

    @Override
    protected BaseRecyclerViewHolder getBaseViewHolder(View v, int viewType) {
        return new UserInfoViewHolder(v,viewType);
    }
//    private BaseViewHolder getTypeViewHolder(View view, int viewType){
//        BaseViewHolder holder = null;
//        switch (viewType){
//
//            case 1:
//                if (tvViewHolder==null)
//                    tvViewHolder = new UserInfoViewHolder(view);
//                holder =tvViewHolder;
//                break;
//            case 2:
//                if (picViewHolder ==null)
//                    picViewHolder =new UserInfoViewHolder(view);
//                holder = picViewHolder;
//                break;
//        }
//        return holder;
//    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getAction();
    }
}
