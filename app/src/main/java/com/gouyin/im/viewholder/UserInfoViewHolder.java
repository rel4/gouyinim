package com.gouyin.im.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gouyin.im.AppConstant;
import com.gouyin.im.ImageServerApi;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseRecyclerViewHolder;
import com.gouyin.im.bean.UserInfoBean;
import com.gouyin.im.bean.UserInfoListBeanDataList;
import com.gouyin.im.login.widget.LoginMainActivity;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.widget.NoScrollGridView;
import com.gouyin.im.widget.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * Created by pc on 2016/6/4.
 */
public class UserInfoViewHolder extends BaseRecyclerViewHolder<UserInfoListBeanDataList> {

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

    TextView tvContent;

    NoScrollGridView gvUserPic;
    private int viewType;
    private GridView mGridView;

    public UserInfoViewHolder(View view, int viewType) {
        super(view);
        this.viewType = viewType;
        initFragment();
    }

    private void initFragment() {
        switch (viewType) {
            case  FLAG_TYPE_PIC_:
            case FLAG_TYPE_PIC_LIST:
                View view = UIUtils.inflateLayout(R.layout.item_user_pic);
                mfragment.addView(view);

                gvUserPic = (NoScrollGridView) view.findViewById(R.id.gv_user_pic);
//                ViewGroup.LayoutParams layoutParams = gvUserPic.getLayoutParams();
//                layoutParams.height= ViewGroup.LayoutParams.MATCH_PARENT;
//                layoutParams.width= ViewGroup.LayoutParams.MATCH_PARENT;
//                gvUserPic.setLayoutParams(layoutParams);
                break;
            case FLAG_TYPE_TV:
                mfragment.addView(UIUtils.inflateLayout(R.layout.item_user_tv));
                break;
        }
    }

    @Override
    protected void onBindData(UserInfoListBeanDataList bean) {
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

    private void setTVData(UserInfoListBeanDataList bean) {

    }

    private void setPICData(UserInfoListBeanDataList bean) {
        if (bean == null)
            return;
        String img = bean.getImg().trim();
        if (StringUtis.isEmpty(img))
            return;
        if (img.contains(AppConstant.IMAGE_SPLIT)) {
            String[] split = img.split(AppConstant.IMAGE_SPLIT);
            ArrayList<String> smallImages = new ArrayList<String>();
            ArrayList<String> bigImages = new ArrayList<String>();
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                if (s.contains(AppConstant.SMALL_IMAGE_SPLIT)) {
                    String[] split1 = s.split(AppConstant.SMALL_IMAGE_SPLIT);
                    if (split1 != null && split1.length >= 2) {
                        smallImages.add(split1[0]);
                        bigImages.add(split1[1]);
                    }
                }

            }
            gvUserPic.setAdapter(new PicGridView(smallImages));


        }

    }

    @Override
    protected void onItemclick(View view, UserInfoListBeanDataList bean, int position) {
        LogUtils.e("MyAdapter", " position : " + position + "-----------msg  : " + (bean.getTitle()));
        UIUtils.startActivity(LoginMainActivity.class);
    }

    private class PicGridView extends BaseAdapter {
        private List<String> list;

        public PicGridView(List<String> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
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
            ImageView imageView = new ImageView(parent.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            LogUtils.e("MyAdapter", "ImageView  position" + position);
            ImageServerApi.showURLImage(imageView, list.get(position));

//            ImageView imageView;
//            if (convertView == null) {
//                imageView = new ImageView(ConfigUtils.getInstance().getActivityContext());
//                imageView.setLayoutParams(new GridView.LayoutParams(75, 75));//设置ImageView对象布局
//                imageView.setAdjustViewBounds(false);//设置边界对齐
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置刻度的类型
//                imageView.setPadding(8, 8, 8, 8);//设置间距
//            }
//            else {
//                imageView = (ImageView) convertView;
//            }
//            imageView.setImageResource(R.mipmap.ic_launcher);//为ImageView设置图片资源
            LogUtils.e("MyAdapter", "ImageView  position-->" + position);
            return imageView;
        }
    }


}
