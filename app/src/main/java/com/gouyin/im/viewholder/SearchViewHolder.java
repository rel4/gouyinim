package com.gouyin.im.viewholder;

import android.view.View;
import android.widget.TextView;

import com.gouyin.im.ImageServerApi;
import com.gouyin.im.R;
import com.gouyin.im.adapter.SearchAdapter;
import com.gouyin.im.base.BaseRecyclerViewHolder;
import com.gouyin.im.bean.SearchReasonBaen;
import com.gouyin.im.widget.RoundedImageView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jb on 2016/7/10.
 */
public class SearchViewHolder extends BaseRecyclerViewHolder<SearchReasonBaen.DataBean> {
    @Bind(R.id.riv_user_image)
    RoundedImageView rivUserImage;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.tv_user_fen)
    TextView tvUserFen;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_submit)
    View tvSubmit;
    private SearchAdapter adapter;

    public SearchViewHolder(View view) {
        super(view);
    }


    @Override
    public void onBindData(SearchReasonBaen.DataBean dataBean, int position) {
        ImageServerApi.showURLSamllImage(rivUserImage, dataBean.getFace());
        tvContent.setText(dataBean.getSignature());
        tvUserName.setText(dataBean.getNickname());
        tvSubmit.setTag(position);
    }

    @Override
    protected void onBindData(SearchReasonBaen.DataBean dataBean) {

    }

    @Override
    protected void onItemclick(View view, SearchReasonBaen.DataBean dataBean, int position) {

    }

    @OnClick(R.id.tv_submit)
    public void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof Integer) {
            int positin = (int) tag;

            adapter.setClick(positin);
        }
    }

    public void setAdapter(SearchAdapter adapter) {
        this.adapter = adapter;
    }
}
