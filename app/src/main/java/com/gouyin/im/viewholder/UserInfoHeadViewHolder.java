package com.gouyin.im.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gouyin.im.ImageServerApi;
import com.gouyin.im.R;
import com.gouyin.im.bean.UserInfoDetailBean;
import com.gouyin.im.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jb on 2016/6/22.
 */
public class UserInfoHeadViewHolder {
    @Bind(R.id.user_background)
    ImageView userBackground;
    @Bind(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.tv_fen_number)
    TextView tvFenNumber;
    @Bind(R.id.tv_wacth_number)
    TextView tvWacthNumber;
    @Bind(R.id.tv_dynamic_number)
    TextView tvDynamicNumber;
    @Bind(R.id.tv_flower_number)
    TextView tvFlowerNumber;
    @Bind(R.id.tv_wacth)
    TextView tvWacth;
    private UserInfoDetailBean userInfodetail;

    public UserInfoHeadViewHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public void setUserInfodetail(UserInfoDetailBean userInfodetail) {
        if (userInfodetail == null || userInfodetail.getData() == null)
            return;
        UserInfoDetailBean.UserInfoDetailDataBean data = userInfodetail.getData();
        UserInfoDetailBean.UserInfoDetailDataBean.Addons addons = data.getAddons();
        UserInfoDetailBean.UserInfoDetailDataBean.Baseinfo baseinfo = data.getBaseinfo();


        ImageServerApi.showURLImage(userBackground, baseinfo.getLikeImage());
        ImageServerApi.showURLImage(ivUserIcon, baseinfo.getFace());
        tvUserName.setText(baseinfo.getNickname());
        tvFenNumber.setText(addons.getUfann());
        tvDynamicNumber.setText(addons.getUlatn());
        tvFlowerNumber.setText(addons.getUfann());
        tvWacthNumber.setText(addons.getUfoln());
        tvWacth.setText(getwacthStatus(data.getFollow()));
    }

    private String getwacthStatus(String str) {
        String status = null;
        if ("1".equals(str)) {
            status = UIUtils.getStringRes(R.string.already) + UIUtils.getStringRes(R.string.wacth);
        } else if ("2".equals(str))
            status = UIUtils.getStringRes(R.string.one_another) + UIUtils.getStringRes(R.string.wacth);
        else if ("3".equals(str))
            status = UIUtils.getStringRes(R.string.no) + UIUtils.getStringRes(R.string.wacth);

        return status;
    }
}
