package com.gouyin.im.home.adapter;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gouyin.im.R;
import com.gouyin.im.bean.GoodSelectBaen;
import com.gouyin.im.main.widget.UserInfoActivity;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.widget.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jianghejie on 15/11/26.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
    public List<GoodSelectBaen> datas = null;
    private String TAG ="MyAdapter";
    public <T> MyAdapter(List<GoodSelectBaen> datas) {
        this.datas = datas;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LogUtils.e(TAG ,"viewType  : "+viewType);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_one_menu, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
            if (position%2==0)
                return super.getItemViewType(position)+1;
        return super.getItemViewType(position);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        GoodSelectBaen goodSelectBaen = datas.get(position);
        if (goodSelectBaen != null)
            viewHolder.tvStr.setText(goodSelectBaen.getMsg());
        viewHolder.setOnclick(viewHolder.getRootView(), position);
    }


    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    public <T> void addData(List<GoodSelectBaen> listData) {
        if (datas == null)
            datas = new ArrayList<GoodSelectBaen>();
        datas.addAll(listData);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }


//    //自定义的ViewHolder，持有每个Item的的所有界面元素
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public RoundedImageView mTextView;
//
//        public ViewHolder(View view) {
//            super(view);
//            mTextView = (RoundedImageView) view.findViewById(R.id.riv_user_image);
//        }
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.riv_user_image)
        RoundedImageView rivUserImage;
        @Bind(R.id.tv_str)
        TextView tvStr;
        private View mView;

        ViewHolder(View view) {
            super(view);
            this.mView = view;
            ButterKnife.bind(this, view);
        }

        public View getRootView() {
            return mView;
        }

        public void setOnclick(View view, final int position) {
            if (mView != null)
                mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtils.e("MyAdapter", " position : " + position +"-----------msg  : "+ (datas.get(position).getMsg()));
                    }
                });
        }

    }

}
