package com.gouyin.im.main.widget;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.main.presenter.RedpacketAcitivityPresenter;
import com.gouyin.im.main.presenter.RedpacketAcitivityPresenterImpl;
import com.gouyin.im.main.view.PlayUserAcitivityView;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jb on 2016/6/24.
 */
public class RedpacketAcitivity extends BaseActivity implements PlayUserAcitivityView {
    private String[] moneys = {};
    @Bind(R.id.iv_user_icon)
    TextView ivUserIcon;
    @Bind(R.id.gv_pic_list)
    GridView gvPicList;
    @Bind(R.id.et_input_money)
    EditText editText;
    private  String uid;
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

    }

    @Override
    protected View setRootContentView() {
        presenter = new RedpacketAcitivityPresenterImpl();
        presenter.attachView(this);
        return UIUtils.inflateLayout(R.layout.activity_red_packet);
    }

    @Override
    protected void initView() {
        gvPicList.setAdapter(new RedpacketAdapter());
    }

    @OnClick({R.id.action_back, R.id.tv_weixin_play, R.id.tv_aliplay_play})
    public void onClick(View view) {
        presenter.swicthAction(view.getId());

    }

    @Override
    public void swicthAliPlay() {
        if (editText!=null){
            String trim = editText.getText().toString().trim();
            if (StringUtis.isEmpty(trim)){
                showToast(getString(R.string.input_money)+getResources().getString(R.string.not_empty));
                return;
            }
           presenter.aliPay(uid,trim);
        }
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
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(parent.getContext());
            textView.setBackgroundResource(R.mipmap.red_packet);
            textView.setText(position + "");
            textView.setGravity(Gravity.CENTER);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editText != null)
                        editText.setText(((TextView) v).getText().toString());
                }
            });
            return textView;
        }
    }
}
