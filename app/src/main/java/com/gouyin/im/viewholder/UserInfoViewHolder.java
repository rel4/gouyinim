package com.gouyin.im.viewholder;

import android.view.View;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseViewHolder;
import com.gouyin.im.bean.GoodSelectBaen;
import com.gouyin.im.bean.UserInfoBean;
import com.gouyin.im.main.widget.UserInfoActivity;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.widget.RoundedImageView;

import butterknife.Bind;


/**
 * Created by pc on 2016/6/4.
 */
public class UserInfoViewHolder extends BaseViewHolder<UserInfoBean> {
    @Bind(R.id.riv_user_image)
    RoundedImageView rivUserImage;
    @Bind(R.id.tv_str)
    TextView tvStr;
    private UserInfoBean goodSelectBaen;
    public UserInfoViewHolder(View view) {
        super(view);

    }

    @Override
    protected void onBindData(UserInfoBean goodSelectBaen) {
        this.goodSelectBaen =goodSelectBaen;
        tvStr.setText(goodSelectBaen.getName());
    }



    @Override
    protected void onItemclick(View view, int position) {
        LogUtils.e("MyAdapter", " position : " + position +"-----------msg  : "+ (goodSelectBaen.getName()));
//        UIUtils.startActivity(UserInfoActivity.class);
    }

}
