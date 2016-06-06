package com.gouyin.im.viewholder;

import android.view.View;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseViewHolder;

import com.gouyin.im.bean.GoodSelectBaen;
import com.gouyin.im.main.widget.UserInfoActivity;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.widget.RoundedImageView;

import butterknife.Bind;


/**
 * Created by pc on 2016/6/4.
 */
public class GoodSelectViewHolder extends BaseViewHolder<GoodSelectBaen> {
    @Bind(R.id.riv_user_image)
    RoundedImageView rivUserImage;
    @Bind(R.id.tv_str)
    TextView tvStr;
    private GoodSelectBaen goodSelectBaen;
    public GoodSelectViewHolder(View view) {
        super(view);

    }

    @Override
    protected void onBindData(GoodSelectBaen goodSelectBaen) {
        this.goodSelectBaen =goodSelectBaen;
        tvStr.setText(goodSelectBaen.getMsg());
    }



    @Override
    protected void onItemclick(View view, int position) {
        LogUtils.e("MyAdapter", " position : " + position +"-----------msg  : "+ (goodSelectBaen.getMsg()));
        UIUtils.startActivity(UserInfoActivity.class);
    }

}
