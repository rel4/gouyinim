package com.moonsister.im.viewholder;

import android.view.View;
import android.widget.TextView;

import com.moonsister.im.ImageServerApi;
import com.moonsister.im.R;
import com.moonsister.im.base.BaseRecyclerViewHolder;
import com.moonsister.im.bean.CommentDataListBean;
import com.moonsister.im.utils.ActivityUtils;
import com.moonsister.im.utils.TimeUtils;
import com.moonsister.im.widget.RoundedImageView;

import butterknife.Bind;

/**
 * Created by pc on 2016/6/8.
 */
public class DynamicCommentViewHolder extends BaseRecyclerViewHolder<CommentDataListBean.DataBean> {
    @Bind(R.id.riv_item_user_image)
    RoundedImageView rivItemUserImage;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_comment_content)
    TextView tvCommentContent;

    public DynamicCommentViewHolder(View view) {
        super(view);
    }

    @Override
    protected void onBindData(CommentDataListBean.DataBean bean) {
        if (bean == null)
            return;
        ImageServerApi.showURLSamllImage(rivItemUserImage, bean.getFace());
        tvCommentContent.setText(bean.getTitle());
        tvTime.setText(TimeUtils.format(bean.getCreate_time() * 1000));
        tvUserName.setText(bean.getNickname());
        rivItemUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startUserinfoActivity(bean.getUid());
            }
        });
    }

    @Override
    protected void onItemclick(View view, CommentDataListBean.DataBean baseBean, int position) {

    }
}
