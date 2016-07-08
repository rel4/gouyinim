package com.gouyin.im.viewholder;

import android.view.View;
import android.widget.TextView;

import com.gouyin.im.ImageServerApi;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseRecyclerViewHolder;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.bean.BaseDataBean;
import com.gouyin.im.bean.CommentDataListBean;
import com.gouyin.im.utils.TimeUtils;
import com.gouyin.im.widget.RoundedImageView;

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
    }

    @Override
    protected void onItemclick(View view, CommentDataListBean.DataBean baseBean, int position) {

    }
}
