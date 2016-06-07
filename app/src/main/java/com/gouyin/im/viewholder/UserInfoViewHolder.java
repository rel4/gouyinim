package com.gouyin.im.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseViewHolder;
import com.gouyin.im.bean.UserInfoBean;
import com.gouyin.im.main.widget.DynamicDatailsActivity;
import com.gouyin.im.main.widget.UserInfoActivity;
import com.gouyin.im.utils.ConfigUtils;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.widget.NoScrollGridView;
import com.gouyin.im.widget.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * Created by pc on 2016/6/4.
 */
public class UserInfoViewHolder extends BaseViewHolder<UserInfoBean> {
    public static final int FLAG_TYPE_TV = 1;
    public static final int FLAG_TYPE_PIC = 2;
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
            case FLAG_TYPE_PIC:
                View view = UIUtils.inflateLayout(R.layout.item_user_pic);
                mfragment.addView(view);

                gvUserPic =(NoScrollGridView) view.findViewById(R.id.gv_user_pic);
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
    protected void onBindData(UserInfoBean bean) {
        LogUtils.e(this,"onBindData " +bean.getName());
        tvStr.setText(bean.getName());
        switch (bean.getAction()) {
            case FLAG_TYPE_PIC:
                setPICData(bean);
                break;
            case FLAG_TYPE_TV:
                setTVData(bean);
                break;

        }
    }

    private void setTVData(UserInfoBean bean) {

    }

    private void setPICData(UserInfoBean bean) {
        ArrayList<String> ls =new ArrayList<String>();
        for (int i =0 ;i<9;i++)
            ls.add("pic id is "+i);
        LogUtils.e(this,"ArrayList size : "+ls.size());
        gvUserPic.setAdapter(new PicGridView(ls));
    }

    @Override
    protected void onItemclick(View view, UserInfoBean bean, int position) {
        LogUtils.e("MyAdapter", " position : " + position + "-----------msg  : " + (bean.getName()));
        UIUtils.startActivity(DynamicDatailsActivity.class);
    }
    private class PicGridView extends BaseAdapter{
        private List<String> list;
        public  PicGridView(List<String> list){
            this.list =list;
        }
        @Override
        public int getCount() {
            return list==null?0:list.size();
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
            imageView.setImageResource(R.mipmap.ic_launcher);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            LogUtils.e("MyAdapter","ImageView  position" +position);


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
            LogUtils.e("MyAdapter","ImageView  position-->" +position);
            return imageView;
        }
    }


}
