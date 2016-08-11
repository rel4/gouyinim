package com.moonsister.tcjy.viewholder.dynamic;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.moonsister.tcjy.ImageServerApi;
import com.moonsister.tcjy.R;
import com.moonsister.tcjy.adapter.DynamicAdapter;
import com.moonsister.tcjy.base.BaseIModel;
import com.moonsister.tcjy.base.BaseRecyclerViewHolder;
import com.moonsister.tcjy.bean.DefaultDataBean;
import com.moonsister.tcjy.bean.UserInfoListBean;
import com.moonsister.tcjy.main.model.UserActionModelImpl;
import com.moonsister.tcjy.utils.ActivityUtils;
import com.moonsister.tcjy.utils.ConfigUtils;
import com.moonsister.tcjy.utils.StringUtis;
import com.moonsister.tcjy.utils.TimeUtils;
import com.moonsister.tcjy.utils.UIUtils;
import com.moonsister.tcjy.widget.RoundedImageView;
import com.moonsister.tcjy.widget.speak.VoicePlay;

import butterknife.Bind;

/**
 * Created by jb on 2016/8/11.
 */
public class VoiceViewHolder extends BaseRecyclerViewHolder<UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList> {
    @Bind(R.id.riv_user_image)
    RoundedImageView rivUserImage;
    @Bind(R.id.tv_user_name)
    TextView tvName;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_wacth_number)
    TextView tv_wacth_number;
    @Bind(R.id.tv_reply_number)
    TextView tv_reply_number;
    @Bind(R.id.tv_play_number)
    TextView tv_play_number;
    @Bind(R.id.tv_add_v)
    ImageView tv_add_v;
    @Bind(R.id.tv_more__number)
    ImageView tv_more__number;
    @Bind(R.id.iv_play_background)
    ImageView iv_play_background;
    @Bind(R.id.tv_show_redpacket)
    TextView tv_show_redpacket;
    @Bind(R.id.iv_play)
    ImageView iv_play;

    public VoiceViewHolder(View view) {
        super(view);
    }

    private boolean isAction = false;

    @Override
    public void onBindData(UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList bean) {
        if (bean == null)
            return;
        ImageServerApi.showURLImage(iv_play_background, bean.getVimg());
        tvContent.setText(bean.getTitle());
        tvTime.setText(TimeUtils.getDynamicTimeString(bean.getCreate_time()));
        tv_play_number.setText(bean.getLkpicn() + "");
        dynamicAction(bean);
        tv_reply_number.setText(bean.getLcomn() + "");
        tv_wacth_number.setText(bean.getLupn() + "");
        tvName.setText(bean.getNickname());
        if (bean.getType() == DynamicAdapter.TYPE_CHARGE_VOICE) {
            if (tv_show_redpacket != null) {
                String format = String.format(UIUtils.getStringRes(R.string.show_wacth_redpacket), bean.getLredn() == null ? 0 : bean.getLredn(), bean.getTmoney() == null ? 0 : bean.getTmoney());
                tv_show_redpacket.setText(format);
                tv_show_redpacket.setVisibility(View.VISIBLE);
            }
            if (tv_play_number != null) {
                tv_play_number.setText(bean.getLredn());
                tv_play_number.setVisibility(View.VISIBLE);
            }
        } else {
            if (tv_show_redpacket != null) {
                tv_show_redpacket.setVisibility(View.GONE);
            }
            if (tv_play_number != null) {
                tv_play_number.setVisibility(View.GONE);
            }
        }
        if (StringUtis.equals(bean.getIsauth(), "1"))
            tv_add_v.setVisibility(View.VISIBLE);
        else
            tv_add_v.setVisibility(View.GONE);
        ImageServerApi.showURLImage(rivUserImage, bean.getFace());
        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtis.equals(bean.getIspay(), "2")) {
                    ActivityUtils.startPayDynamicRedPackketActivity(bean.getMoney(), bean.getLatest_id());
                } else {
                    VoicePlay voicePlay = new VoicePlay();
                    voicePlay.playVoice(ConfigUtils.getInstance().getApplicationContext(), bean.getVideo());
                }

            }
        });
    }

    @Override
    protected void onItemclick(View view, UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList bean, int position) {
        ActivityUtils.startDynamicDatailsActivity(bean);
    }

    /**
     * 处理动态行为
     *
     * @param bean
     */
    private void dynamicAction(UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList bean) {
        tv_wacth_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAction) {
                    if (baseIView != null)
                        baseIView.transfePageMsg(UIUtils.getStringRes(R.string.already) + UIUtils.getStringRes(R.string.action));
                    return;
                }
                isAction = true;
                if (baseIView != null)
                    baseIView.showLoading();
                UserActionModelImpl userActionModel = new UserActionModelImpl();
                userActionModel.likeAction(bean.getLatest_id(), "1", new BaseIModel.onLoadDateSingleListener<DefaultDataBean>() {
                    @Override
                    public void onSuccess(DefaultDataBean b, BaseIModel.DataType dataType) {
                        if (baseIView != null) {
                            baseIView.hideLoading();
                            baseIView.transfePageMsg(b.getMsg());
                        }
                        String s = tv_wacth_number.getText().toString();
                        int i = StringUtis.string2Int(s) + 1;
                        bean.setLupn(i + "");
                        tv_wacth_number.setText(bean.getLupn());
                    }

                    @Override
                    public void onFailure(String msg) {
                        if (baseIView != null) {
                            baseIView.hideLoading();
                            baseIView.transfePageMsg(msg);
                        }
                    }
                });
            }
        });
        tv_more__number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startDynamicAtionActivity(bean.getUid(), bean.getLatest_id(), bean.getType(), bean.getIstop());
            }
        });
        rivUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startUserinfoActivity(bean.getUid());
            }
        });
    }
}
