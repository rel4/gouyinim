package com.gouyin.im.viewholder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gouyin.im.ImageServerApi;
import com.gouyin.im.R;
import com.gouyin.im.bean.UserInfoDetailBean;
import com.gouyin.im.utils.UIUtils;

import butterknife.Bind;

/**
 * Created by jb on 2016/6/25.
 */
public class PersonDynamicViewholder extends BaseHolder<UserInfoDetailBean> {
    @Bind(R.id.user_background)
    ImageView userBackground;
    @Bind(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.tv_user_all_income)
    TextView tvUserAllIncome;
    @Bind(R.id.tv_user_day_income)
    TextView tvUserDayIncome;
    @Bind(R.id.tv_fen_number)
    TextView tvFenNumber;
    @Bind(R.id.tv_wacth_number)
    TextView tvWacthNumber;
    @Bind(R.id.tv_dynamic_number)
    TextView tvDynamicNumber;
    @Bind(R.id.tv_flower_number)
    TextView tvFlowerNumber;


    @Override
    protected View initView() {
        View headView = UIUtils.inflateLayout(R.layout.head_person_info);
        return headView;
    }

    @Override
    public void refreshView(UserInfoDetailBean data) {
        if (data == null)
            return;
        UserInfoDetailBean.UserInfoDetailDataBean bean = data.getData();
        if (bean == null)
            return;
        UserInfoDetailBean.UserInfoDetailDataBean.Baseinfo baseinfo = bean.getBaseinfo();
        if (baseinfo != null) {
            ImageServerApi.showURLBigImage(userBackground, baseinfo.getLike_image());
            ImageServerApi.showURLSamllImage(ivUserIcon, baseinfo.getLikeImage());
            tvUserName.setText(baseinfo.getNickname());

        }
        UserInfoDetailBean.UserInfoDetailDataBean.Addons addons = bean.getAddons();
        if (addons != null) {
            tvUserAllIncome.setText(addons.getIncome_all());
            tvUserDayIncome.setText(addons.getIncome_today());
            tvFenNumber.setText(addons.getUfann());
            tvDynamicNumber.setText(addons.getUlatn());
            tvFlowerNumber.setText(addons.getUfann());
            tvWacthNumber.setText(addons.getUfoln());
        }


    }
}
