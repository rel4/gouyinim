package com.gouyin.im.viewholder;

import android.view.View;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseRecyclerViewHolder;
import com.gouyin.im.bean.TiXinrRecordBean;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.TimeUtils;

import butterknife.Bind;

/**
 * Created by jb on 2016/7/2.
 */
public class TixianViewHolder extends BaseRecyclerViewHolder<TiXinrRecordBean.DataBean> {
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_time)
    TextView tvTime;

    public TixianViewHolder(View view) {
        super(view);
    }

    @Override
    protected void onBindData(TiXinrRecordBean.DataBean dataBean) {
        tvMoney.setText(dataBean.getMoney());
        tvTime.setText(TimeUtils.format(dataBean.getAudit_time()));
    }

    @Override
    protected void onItemclick(View view, TiXinrRecordBean.DataBean dataBean, int position) {
        ActivityUtils.startTiXianRecordActivity(dataBean);
    }
}
