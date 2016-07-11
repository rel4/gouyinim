package com.gouyin.im.main.widget;

import android.view.View;
import android.widget.TextView;

import com.gouyin.im.ImageServerApi;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.bean.UserInfoChangeBean;
import com.gouyin.im.main.presenter.UserinfoActivityPresenter;
import com.gouyin.im.main.presenter.UserinfoActivityPresenterImpl;
import com.gouyin.im.main.view.UserinfoActivityView;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;
import com.gouyin.im.widget.RoundedImageView;

import butterknife.Bind;

/**
 * Created by jb on 2016/7/11.
 */
public class UserinfoActivity extends BaseActivity implements UserinfoActivityView {
    @Bind(R.id.riv_user_image)
    RoundedImageView rivUserImage;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_level)
    TextView tvLevel;
    @Bind(R.id.tv_signature)
    TextView tvSignature;
    @Bind(R.id.tv_introduction)
    TextView tvIntroduction;
    @Bind(R.id.tv_height)
    TextView tvHeight;
    @Bind(R.id.tv_weight)
    TextView tvWeight;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_job)
    TextView tvJob;
    @Bind(R.id.tv_education)
    TextView tvEducation;
    private UserinfoActivityPresenter presenter;
    private String uid;

    @Override
    protected View setRootContentView() {
        uid = getIntent().getStringExtra("uid");
        if (StringUtis.isEmpty(uid))
            finish();
        return UIUtils.inflateLayout(R.layout.activity_userinfo);
    }

    @Override
    protected void initView() {
        presenter = new UserinfoActivityPresenterImpl();
        presenter.attachView(this);
        presenter.loadBasicData(uid);
    }

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
    public void setUserInfo(UserInfoChangeBean userInfoChangeBean) {
        if (userInfoChangeBean == null)
            return;
        UserInfoChangeBean.DataBean data = userInfoChangeBean.getData();
        if (data == null)
            return;
        ImageServerApi.showURLSamllImage(rivUserImage, data.getFace());
//        tvnike.setText(data.getNickname());
        tvSex.setText(data.getSex() == 1 ? "男" : "女");
//        tvBirthday.setText(data.getBirthday());
        tvAddress.setText(data.getResidence());
        tvSignature.setText(data.getSignature());
        tvIntroduction.setText(data.getSelf_intro());
        tvEducation.setText(data.getDegree());
        tvHeight.setText(data.getHeight());
        tvWeight.setText(data.getWeight());
        tvJob.setText(data.getProfession());
    }


}
