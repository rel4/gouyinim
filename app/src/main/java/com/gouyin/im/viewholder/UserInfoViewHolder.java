package com.gouyin.im.viewholder;

import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.gouyin.im.ImageServerApi;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseRecyclerViewHolder;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.TimeUtils;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.widget.NoScrollGridView;
import com.gouyin.im.widget.RoundedImageView;

import butterknife.Bind;


/**
 * Created by pc on 2016/6/4.
 */
public class UserInfoViewHolder extends BaseRecyclerViewHolder<UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList> {

    //1 红包图集
    public static final int TYPE_REDPACTKET_DYNAMIC = 1;

    //2 普通图片
    public static final int TYPE_DEFAULT_DYNAMIC = 2;
    //3 视频
    public static final int TYPE_VIDEO_DYNAMIC = 3;


    @Bind(R.id.riv_user_image)
    RoundedImageView rivUserImage;
    @Bind(R.id.tv_user_name)
    TextView tvStr;
    @Bind(R.id.layout_content)
    FrameLayout mfragment;
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
    NoScrollGridView gvUserPic;
    private int viewType;
    private GridView mGridView;
    private TextView tvPlay;
    private ImageView playBackground;
    private VideoView play;
    private TextView tv_show_redpacket;

    public UserInfoViewHolder(View view, int viewType) {
        super(view);
        this.viewType = viewType;
        initFragment();
    }

    private void initFragment() {
        switch (viewType) {
            case TYPE_DEFAULT_DYNAMIC:
            case TYPE_REDPACTKET_DYNAMIC:
                View view = UIUtils.inflateLayout(R.layout.item_user_pic);
                mfragment.removeAllViews();
                mfragment.addView(view);

                gvUserPic = (NoScrollGridView) view.findViewById(R.id.gv_user_pic);
                tv_show_redpacket = (TextView) view.findViewById(R.id.tv_show_redpacket);
                break;
            case TYPE_VIDEO_DYNAMIC:
                mfragment.removeAllViews();
                View palyView = UIUtils.inflateLayout(R.layout.item_user_tv);
                tvPlay = (TextView) palyView.findViewById(R.id.tv_play_content);
                playBackground = (ImageView) palyView.findViewById(R.id.iv_play_background);
                play = (VideoView) palyView.findViewById(R.id.play);
                mfragment.addView(palyView);
                break;
        }
    }

    @Override
    public void onBindData(UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList bean) {
        tvStr.setText(bean.getTitle());
        switch (bean.getType()) {
            case TYPE_REDPACTKET_DYNAMIC:
                setPICData(bean);
                break;
            case TYPE_DEFAULT_DYNAMIC:
                setPICData(bean);
                break;
            case TYPE_VIDEO_DYNAMIC:
                setTVData(bean);
                break;

        }
    }

    /**
     * 视频信息
     *
     * @param bean
     */
    private void setTVData(UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList bean) {
        if (bean == null)
            return;
        ImageServerApi.showURLImage(playBackground, bean.getVimg());
        tvContent.setText(bean.getTitle());
        tvTime.setText(TimeUtils.format(bean.getCreate_time() * 1000));
        tv_play_number.setText(bean.getLkpicn() + "");
        tv_reply_number.setText(bean.getLcomn() + "");
        tv_wacth_number.setText(bean.getLupn() + "");
        tvStr.setText(bean.getNickname());

        ImageServerApi.showURLImage(rivUserImage, bean.getFace());
        playBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (play != null) {
                    play.setVideoPath(bean.getVideo());

                    play.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            playBackground.setVisibility(View.GONE);
                            play.start();
                        }
                    });

                    play.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            playBackground.setVisibility(View.VISIBLE);

                        }
                    });
                }
            }
        });
    }

    /**
     * 图片信息
     *
     * @param bean
     */
    private void setPICData(UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList bean) {
        if (bean == null)
            return;

        tvContent.setText(bean.getTitle());
        tvTime.setText(TimeUtils.format(bean.getCreate_time() * 1000));

        tv_reply_number.setText(bean.getLcomn());
        tv_wacth_number.setText(bean.getLupn());
        tvStr.setText(bean.getNickname());
        ImageServerApi.showURLImage(rivUserImage, bean.getFace());

        if (bean.getType() == TYPE_REDPACTKET_DYNAMIC) {
            if (tv_show_redpacket != null) {
                String format = String.format(UIUtils.getStringRes(R.string.show_wacth_redpacket), bean.getLredn(), bean.getTmoney());
                tv_show_redpacket.setText(format);
                tv_show_redpacket.setVisibility(View.VISIBLE);
            }
            if (tv_play_number != null) {
                tv_play_number.setText(bean.getLkpicn());
                tv_play_number.setVisibility(View.VISIBLE);
            }
        } else {
            if (tv_show_redpacket != null) {
                tv_show_redpacket.setVisibility(View.INVISIBLE);
            }
            if (tv_play_number != null) {
                tv_play_number.setVisibility(View.INVISIBLE);
            }
        }
        gvUserPic.setAdapter(new PicGridView(bean));

    }

    @Override
    protected void onItemclick(View view, UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList bean, int position) {
        ActivityUtils.startDynamicDatailsActivity(bean);
    }

    private class PicGridView extends BaseAdapter {
        private UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList bean;

        public PicGridView(UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList bean) {
            this.bean = bean;
        }

        @Override
        public int getCount() {
            return bean.getSimg() == null ? 0 : bean.getSimg().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = UIUtils.inflateLayout(R.layout.item_dynamic_pics);
            ImageView pic = (ImageView) view.findViewById(R.id.iv_pic);
            ImageServerApi.showURLImage(pic, bean.getSimg().get(position));
            pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (StringUtis.equals(bean.getIspay(), "2")) {
                        ActivityUtils.startPayDynamicRedPackketActivity(bean.getMoney(), bean.getLatest_id());
                    } else {
                        ActivityUtils.startImagePagerActivity(bean.getImg(), position);
                    }
                }
            });
            return pic;
        }
    }


}
