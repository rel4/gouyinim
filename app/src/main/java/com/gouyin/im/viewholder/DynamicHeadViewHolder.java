package com.gouyin.im.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gouyin.im.AppConstant;
import com.gouyin.im.ImageServerApi;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseIModel;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.bean.UserInfoDetailBean;
import com.gouyin.im.main.model.UserActionModelImpl;
import com.gouyin.im.main.widget.DynamicActivity;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jb on 2016/6/22.
 */
public class DynamicHeadViewHolder {
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
    @Bind(R.id.layout_click_wacth)
    RelativeLayout layout_click_wacth;
    @Bind(R.id.tv_wacth_status)
    ImageView tv_wacth_status;
    private UserInfoDetailBean userInfodetail;
    private DynamicActivity userInfoView;

    public DynamicHeadViewHolder(View view) {
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
        tvFlowerNumber.setText(addons.getUflon());
        tvWacthNumber.setText(addons.getUfoln());
        getwacthStatus(data.getFollow(), data.getUid());
        ivUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startUserinfoActivity(userInfodetail.getData().getUid());
            }
        });

    }

    private void getwacthStatus(String str, String uid) {
        if ("1".equals(str)) {
            layout_click_wacth.setVisibility(View.GONE);
            ImageServerApi.showResourcesImage(tv_wacth_status, R.mipmap.btn_attention_has_been);
        } else if ("2".equals(str)) {
            layout_click_wacth.setVisibility(View.GONE);
            ImageServerApi.showResourcesImage(tv_wacth_status, R.mipmap.btn_attention);
        } else if ("3".equals(str)) {
            layout_click_wacth.setVisibility(View.VISIBLE);
            layout_click_wacth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userInfoView.showLoading();
                    UserActionModelImpl userActionModel = new UserActionModelImpl();
                    userActionModel.wacthAction(uid, "1", new BaseIModel.onLoadDateSingleListener<DefaultDataBean>() {
                        @Override
                        public void onSuccess(DefaultDataBean bean, BaseIModel.DataType dataType) {
                            if (bean != null) {
                                if (StringUtis.equals(bean.getCode(), AppConstant.code_request_success)) {
                                    layout_click_wacth.setVisibility(View.GONE);
                                    ImageServerApi.showResourcesImage(tv_wacth_status, R.mipmap.btn_attention_has_been);
                                }
                                userInfoView.transfePageMsg(bean.getMsg());
                            } else {
                                userInfoView.transfePageMsg(UIUtils.getStringRes(R.string.request_failed));
                            }
                            userInfoView.hideLoading();
                        }

                        @Override
                        public void onFailure(String msg) {
                            userInfoView.hideLoading();
                            userInfoView.transfePageMsg(msg);
                        }
                    });
                }
            });
        }


    }

    public void setUserInfoView(DynamicActivity userInfoView) {
        this.userInfoView = userInfoView;
    }

    @OnClick(R.id.iv_back)
    public void onClick(View view) {
        userInfoView.pageFinish();
    }
}
