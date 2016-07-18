package com.moonsister.im.my.widget;

import android.view.View;
import android.widget.TextView;

import com.moonsister.im.ImageServerApi;
import com.moonsister.im.R;
import com.moonsister.im.base.BaseActivity;
import com.moonsister.im.bean.PersonInfoDetail;
import com.moonsister.im.event.Events;
import com.moonsister.im.event.RxBus;
import com.moonsister.im.login.widget.SelectPicPopupActivity;
import com.moonsister.im.main.widget.PersonInfoChangeActivity;
import com.moonsister.im.manager.UserInfoManager;
import com.moonsister.im.utils.ActivityUtils;
import com.moonsister.im.utils.LogUtils;
import com.moonsister.im.utils.StringUtis;
import com.moonsister.im.utils.UIUtils;
import com.moonsister.im.widget.RoundedImageView;
import com.trello.rxlifecycle.ActivityEvent;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jb on 2016/6/27.
 */
public class RZFirstActivity extends BaseActivity {
    @Bind(R.id.iv_avater)
    RoundedImageView ivAvater;
    @Bind(R.id.tv_nike)
    TextView tvNike;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_height)
    TextView tvHeight;
    private String avaterPath;

    @Override
    protected View setRootContentView() {
        return UIUtils.inflateLayout(R.layout.activity_rzfirst);
    }

    @Override
    protected String initTitleName() {
        return UIUtils.getStringRes(R.string.apply_renzheng);
    }

    @Override
    protected void initView() {
        ImageServerApi.showURLSamllImage(ivAvater, UserInfoManager.getInstance().getAvater());
        PersonInfoDetail info =
                UserInfoManager.getInstance().getMemoryPersonInfoDetail();
        String sex = info.getSex();
        if (!StringUtis.isEmpty(sex)) {
            if (StringUtis.equals("1", sex)) {
                tvSex.setText(UIUtils.getStringRes(R.string.boy));
            } else {
                tvSex.setText(UIUtils.getStringRes(R.string.girls));
            }

        }
        String nickname = info.getNickname();
        if (!StringUtis.isEmpty(nickname))
            tvNike.setText(nickname);
        setRX();
    }


    private void pageFinish() {
        finish();
    }

    private void setRX() {

        RxBus.with(this)
                .setEndEvent(ActivityEvent.DESTROY)
                .setEvent(Events.EventEnum.CERTIFICATION_PAGE_FINISH)
                .onNext(events -> pageFinish())
                .create();
        RxBus.with(this)
                .setEndEvent(ActivityEvent.DESTROY)
                .setEvent(Events.EventEnum.PERSON_INFO_CHANGE)
                .onNext(events -> {
                    action((Events<PersonInfoChangeActivity.PersonInfoChangeData>) events);
                })
                .create();
    }

    private void action(Events<PersonInfoChangeActivity.PersonInfoChangeData> events) {
        if (events == null)
            return;
        PersonInfoChangeActivity.PersonInfoChangeData message = events.message;

        switch (message.getType().getValue()) {
            case 1:
                tvHeight.setText(message.getArg1());
                break;
            case 2:
                tvSex.setText(message.getArg1());
                break;
            case 3:
                tvNike.setText(message.getArg1());

                break;
            case 4:
                tvAddress.setText(message.getArg1());
                break;
        }
    }


    @OnClick({R.id.layout_nike, R.id.layout_sex, R.id.layout_address, R.id.layout_height, R.id.tv_next, R.id.iv_avater})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_nike:
                ActivityUtils.startPersonInfoChangeActivity(PersonInfoChangeActivity.ChangeType.NIKE);
                break;
            case R.id.layout_sex:
                ActivityUtils.startPersonInfoChangeActivity(PersonInfoChangeActivity.ChangeType.SEX);
                break;
            case R.id.layout_address:
                ActivityUtils.startPersonInfoChangeActivity(PersonInfoChangeActivity.ChangeType.ADDRESS);
                break;
            case R.id.layout_height:
                ActivityUtils.startPersonInfoChangeActivity(PersonInfoChangeActivity.ChangeType.HEIGHT);
                break;
            case R.id.tv_next:
                next();

                break;
            case R.id.iv_avater:
                ActivityUtils.startActivity(SelectPicPopupActivity.class);
                RxBus.with(this)
                        .setEvent(Events.EventEnum.GET_PHOTO)
                        .setEndEvent(ActivityEvent.DESTROY)
                        .onNext((events) -> {
                            String message = (String) events.message;
                            LogUtils.e(RZFirstActivity.class, "pic_path : " + message);
                            avaterPath = message;
                            ImageServerApi.showURLSamllImage(ivAvater, message);
                        }).create();

                break;

        }
    }

    private void next() {
        String address = tvAddress.getText().toString().trim();
        String height = tvHeight.getText().toString().trim();
        String sex = tvSex.getText().toString().trim();
        String nike = tvNike.getText().toString().trim();

        ActivityUtils.startRZSecondActivity(address, height, sex, nike, avaterPath);
    }
}
