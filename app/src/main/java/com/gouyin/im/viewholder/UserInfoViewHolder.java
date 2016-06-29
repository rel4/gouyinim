package com.gouyin.im.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gouyin.im.ImageServerApi;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseRecyclerViewHolder;
import com.gouyin.im.bean.UserInfoListBean;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.LogUtils;
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
    public static final int FLAG_TYPE_PIC_LIST = 1;

    //2 普通图片
    public static final int FLAG_TYPE_PIC_ = 2;
    //3 视频
    public static final int FLAG_TYPE_TV = 3;


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

    public UserInfoViewHolder(View view, int viewType) {
        super(view);
        this.viewType = viewType;
        initFragment();
    }

    private void initFragment() {
        switch (viewType) {
            case FLAG_TYPE_PIC_:
            case FLAG_TYPE_PIC_LIST:
                View view = UIUtils.inflateLayout(R.layout.item_user_pic);
                mfragment.removeAllViews();
                mfragment.addView(view);

                gvUserPic = (NoScrollGridView) view.findViewById(R.id.gv_user_pic);
                break;
            case FLAG_TYPE_TV:
                mfragment.removeAllViews();
                View palyView = UIUtils.inflateLayout(R.layout.item_user_tv);
                tvPlay = (TextView) palyView.findViewById(R.id.tv_play_content);
                playBackground = (ImageView) palyView.findViewById(R.id.iv_play_background);
                mfragment.addView(palyView);
                break;
        }
    }

    @Override
    public void onBindData(UserInfoListBean.UserInfoListBeanData.UserInfoListBeanDataList bean) {
        LogUtils.e(this, "onBindData " + bean.getTitle());
        tvStr.setText(bean.getTitle());
        switch (bean.getType()) {
            case FLAG_TYPE_PIC_LIST:
                setPICData(bean);
                break;
            case FLAG_TYPE_PIC_:
                setPICData(bean);
                break;
            case FLAG_TYPE_TV:
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
        tvTime.setText(TimeUtils.format(bean.getCreate_time()));
        tv_play_number.setText(bean.getLkpicn() + "");
        tv_reply_number.setText(bean.getLcomn() + "");
        tv_wacth_number.setText(bean.getLupn() + "");
        tvStr.setText(bean.getNickname());

        ImageServerApi.showURLImage(rivUserImage, bean.getFace());

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
        tvTime.setText(TimeUtils.format(bean.getCreate_time()));
        tv_play_number.setText(bean.getLkpicn() + "");
        tv_reply_number.setText(bean.getLcomn() + "");
        tv_wacth_number.setText(bean.getLupn() + "");
        tvStr.setText(bean.getNickname());
        ImageServerApi.showURLImage(rivUserImage, bean.getFace());
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
            View view = UIUtils.inflateLayout(R.layout.item_pics);
            ImageView pic = (ImageView) view.findViewById(R.id.iv_pic);
            ImageServerApi.showURLImage(pic, bean.getSimg().get(position));
            pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.getType() == 1) {
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
