package com.gouyin.im.viewholder;

import android.view.View;
import android.widget.TextView;

import com.gouyin.im.ImageServerApi;
import com.gouyin.im.R;
import com.gouyin.im.adapter.FrientAdapter;
import com.gouyin.im.base.BaseRecyclerViewHolder;
import com.gouyin.im.bean.FrientBaen;
import com.gouyin.im.widget.RoundedImageView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jb on 2016/7/8.
 */
public class FrientViewHoler extends BaseRecyclerViewHolder<FrientBaen.DataBean> {
    @Bind(R.id.riv_user_image)
    RoundedImageView rivUserImage;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;
    private FrientBaen.DataBean bean;
    private FrientAdapter adapter;

    public FrientViewHoler(View view) {
        super(view);
    }

    @Override
    protected void onBindData(FrientBaen.DataBean dataBean) {


    }

    @Override
    public void onBindData(FrientBaen.DataBean dataBean, int position) {
        bean = dataBean;
        ImageServerApi.showURLSamllImage(rivUserImage, dataBean.getFace());
        tvContent.setText(dataBean.getSignature());
        tvUserName.setText(dataBean.getNickname());
        tvSubmit.setTag(position);
    }

    @Override
    protected void onItemclick(View view, FrientBaen.DataBean dataBean, int position) {

    }


    @OnClick(R.id.tv_submit)
    public void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof Integer) {
            int positin = (int) tag;

            adapter.setClick(positin);
        }
    }

    public void setAdapter(FrientAdapter adapter) {
        this.adapter = adapter;
    }
}
