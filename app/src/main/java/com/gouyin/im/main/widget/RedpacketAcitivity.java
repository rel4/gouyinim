package com.gouyin.im.main.widget;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gouyin.im.ImageServerApi;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.main.presenter.RedpacketAcitivityPresenter;
import com.gouyin.im.main.presenter.RedpacketAcitivityPresenterImpl;
import com.gouyin.im.main.view.PlayUserAcitivityView;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.widget.RoundedImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jb on 2016/6/24.
 */
public class RedpacketAcitivity extends BaseActivity implements PlayUserAcitivityView {
    @Bind(R.id.iv_user_icon)
    RoundedImageView ivUserIcon;
    @Bind(R.id.gv_pic_list)
    GridView gvPicList;
    @Bind(R.id.et_input_money)
    EditText editText;
    private String uid;
    private int type;
    private String[] moneys;
    private int[] flowers = {R.mipmap.flower_1, R.mipmap.flower_2, R.mipmap.flower_3, R.mipmap.flower_4, R.mipmap.flower_5, R.mipmap.flower_6, R.mipmap.flower_7, R.mipmap.flower_8, R.mipmap.flower_9};
    private String[] flowerNames;

    public enum RedpacketType {
        TYPE_REDPACKET(1), TYPE_FLOWER(2);
        private final int type;

        private RedpacketType(int type) {
            this.type = type;
        }

        public int getValue() {
            return type;
        }

    }

    private void addData() {
        if (1 == type) {
            moneys = UIUtils.getResources().getStringArray(R.array.moneys);
        } else if (2 == type) {
            flowerNames = UIUtils.getResources().getStringArray(R.array.flower_name);

        }
    }

    private RedpacketAcitivityPresenter presenter;

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
    }

    @Override
    public void transfePageMsg(String msg) {
        showToast(msg);
    }

    @Override
    protected View setRootContentView() {

        uid = getIntent().getStringExtra("id");
        type = getIntent().getIntExtra("type", -1);
        addData();
        presenter = new RedpacketAcitivityPresenterImpl();
        presenter.attachView(this);

        return UIUtils.inflateLayout(R.layout.activity_red_packet);
    }

    @Override
    protected void initView() {
        String avater = getIntent().getStringExtra("avater");
        if (!StringUtis.isEmpty(avater))
            ImageServerApi.showURLSamllImage(ivUserIcon, avater);
        gvPicList.setAdapter(new RedpacketAdapter());
    }

    @OnClick({R.id.action_back, R.id.tv_weixin_play, R.id.tv_aliplay_play})
    public void onClick(View view) {
        presenter.swicthAction(view.getId());

    }

    @Override
    public void swicthAliPlay() {
        if (editText != null) {
            String trim = editText.getText().toString().trim();
            if (StringUtis.isEmpty(trim)) {
                showToast(getString(R.string.input_money) + getResources().getString(R.string.not_empty));
                return;
            }
            presenter.aliPay(type, uid, trim);
        }
    }

    @Override
    public void pageFinish() {
        finish();
    }

    private class RedpacketAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 9;
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
            View view = UIUtils.inflateLayout(R.layout.item_redpacket);
            ViewHolder holder = new ViewHolder(view);
            holder.setData(position);

            return holder.getHolerView();
        }

    }

    class ViewHolder {
        private View view;
        @Bind(R.id.iv)
        ImageView iv;
        @Bind(R.id.tv_money)
        TextView tvMoney;
        @Bind(R.id.tv_name)
        TextView tv_name;

        ViewHolder(View view) {
            this.view = view;
            ButterKnife.bind(this, view);

        }

        public View getHolerView() {
            return view;
        }

        public void setData(int position) {
            switch (type) {
                case 1:
                    ImageServerApi.showResourcesImage(iv, R.mipmap.red_packet);
                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editText.setText(moneys[position]);
                        }
                    });
                    tvMoney.setText(moneys[position]);
                    tv_name.setVisibility(View.GONE);
                    tvMoney.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    ImageServerApi.showResourcesImage(iv, flowers[position]);
                    tvMoney.setVisibility(View.GONE);
                    tv_name.setText(flowerNames[position]);
                    tv_name.setVisibility(View.VISIBLE);
                    tv_name.setTextColor(UIUtils.getResources().getColor(R.color.text_black_color));
                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editText.setText(position + "");
                        }
                    });
                    break;
            }
        }
    }
}
