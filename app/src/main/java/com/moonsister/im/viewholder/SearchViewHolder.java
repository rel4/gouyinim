package com.moonsister.im.viewholder;

import android.view.View;
import android.widget.TextView;

import com.moonsister.im.ImageServerApi;
import com.moonsister.im.R;
import com.moonsister.im.adapter.SearchAdapter;
import com.moonsister.im.base.BaseRecyclerViewHolder;
import com.moonsister.im.bean.SearchReasonBaen;
import com.moonsister.im.utils.ActivityUtils;
import com.moonsister.im.utils.UIUtils;
import com.moonsister.im.widget.RoundedImageView;

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
    TextView tv_user_fen;
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
        tv_user_fen.setText(UIUtils.getStringRes(R.string.str_fen) + " : " + dataBean.getFansnum());
        tvSubmit.setTag(position);
        rivUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startUserinfoActivity(dataBean.getUid());
            }
        });
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
