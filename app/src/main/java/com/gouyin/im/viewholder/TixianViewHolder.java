package com.gouyin.im.viewholder;

import android.view.View;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseRecyclerViewHolder;
import com.gouyin.im.bean.TiXinrRecordBean;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.TimeUtils;
import com.gouyin.im.utils.UIUtils;

import butterknife.Bind;

/**
 * Created by jb on 2016/7/2.
 */
public class TixianViewHolder extends BaseRecyclerViewHolder<TiXinrRecordBean.DataBean> {
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_flag)
    TextView tv_flag;

    public TixianViewHolder(View view) {
        super(view);
    }

    @Override
    protected void onBindData(TiXinrRecordBean.DataBean dataBean) {
        tvMoney.setText(dataBean.getMoney());
        tvTime.setText(TimeUtils.format(dataBean.getAudit_time()));
        /**
         * status 提现状态 1申请中 2已通过 3不通过
         */
        String status = dataBean.getStatus();
        if (StringUtis.equals("1",status)){
            tv_flag.setText(UIUtils.getStringRes(R.string.tixining));
        }else if (StringUtis.equals("2",status)){
            tv_flag.setText(UIUtils.getStringRes(R.string.success)+"    ");
        }else if (StringUtis.equals("3",status)){
            tv_flag.setText(UIUtils.getStringRes(R.string.not_success));
        }


    }

    @Override
    protected void onItemclick(View view, TiXinrRecordBean.DataBean dataBean, int position) {
        ActivityUtils.startTiXianRecordActivity(dataBean);
    }
}
