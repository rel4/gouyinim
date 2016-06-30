package com.gouyin.im.main.widget;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gouyin.im.R;
import com.gouyin.im.base.BaseActivity;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.my.widget.info.SelectPlandWindowActivity;
import com.gouyin.im.my.widget.info.SelectSexActivity;
import com.gouyin.im.utils.ActivityUtils;
import com.gouyin.im.utils.StringUtis;
import com.gouyin.im.utils.UIUtils;
import com.trello.rxlifecycle.ActivityEvent;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jb on 2016/6/30.
 */
public class PersonInfoChangeActivity extends BaseActivity {
    @Bind(R.id.tv_title_right)
    TextView tvTitleRight;
    @Bind(R.id.et_info)
    EditText etInfo;
    @Bind(R.id.tv_monad)
    TextView tvMonad;
    @Bind(R.id.tv_Left)
    TextView tv_Left;
    private ChangeType type;

    /**
     * 修改的类型
     */
    public enum ChangeType {
        HEIGHT(1), SEX(2), NIKE(3), ADDRESS(4);
        int chengeType;

        private ChangeType(int type) {
            chengeType = type;
        }

        public int getValue() {
            return chengeType;
        }
    }

    @Override
    protected View setRootContentView() {
        int i = getIntent().getIntExtra("type", 0);
        if (i == 0)
            finish();

        switch (i) {
            case 1:
                type = ChangeType.HEIGHT;
                break;
            case 2:
                type = ChangeType.SEX;
                break;
            case 3:
                type = ChangeType.NIKE;
                break;
            case 4:
                type = ChangeType.ADDRESS;
                break;

        }
        return UIUtils.inflateLayout(R.layout.activity_person_info_change);
    }

    @Override
    protected String initTitleName() {
        setRx();
        String title = null;
        switch (type.getValue()) {
            case 1:
                title = UIUtils.getStringRes(R.string.height);
                tvMonad.setText(UIUtils.getStringRes(R.string.cm));
                tvMonad.setTextColor(UIUtils.getResources().getColor(R.color.text_black_color));
                etInfo.setHint(title);
                break;
            case 2:
                etInfo.setVisibility(View.INVISIBLE);
                title = UIUtils.getStringRes(R.string.sex);
                tvMonad.setVisibility(View.VISIBLE);
                tvMonad.setTextColor(UIUtils.getResources().getColor(R.color.home_navigation_text_red));
                tv_Left.setVisibility(View.VISIBLE);
                tv_Left.setText(UIUtils.getStringRes(R.string.sex));
                tv_Left.setTextColor(UIUtils.getResources().getColor(R.color.text_black_color));
                ActivityUtils.startActivity(SelectSexActivity.class);
                setRx();
                break;
            case 3:
                title = UIUtils.getStringRes(R.string.nike);
                tvMonad.setVisibility(View.GONE);
                break;
            case 4:
                title = UIUtils.getStringRes(R.string.address);
                etInfo.setVisibility(View.INVISIBLE);
                tv_Left.setVisibility(View.VISIBLE);
                tvMonad.setVisibility(View.VISIBLE);
                tvMonad.setTextColor(UIUtils.getResources().getColor(R.color.home_navigation_text_red));
                tv_Left.setText(UIUtils.getStringRes(R.string.Provice) + UIUtils.getResources().getString(R.string.City));
                tv_Left.setTextColor(UIUtils.getResources().getColor(R.color.text_black_color));
                ActivityUtils.startActivity(SelectPlandWindowActivity.class);
                setRx();
                break;
        }
        if (!StringUtis.isEmpty(title))
            return title;
        return super.initTitleName();
    }

    private void setRx() {
        RxBus.with(this)
                .setEndEvent(ActivityEvent.DESTROY)
                .setEvent(Events.EventEnum.SELECT_PLAND_DATA)
                .onNext(events -> {
                    tvMonad.setText((String) events.message);
                })
                .create();
    }

    @Override
    protected void initView() {
        tvTitleRight.setVisibility(View.VISIBLE);
        tvTitleRight.setText(UIUtils.getStringRes(R.string.finish));
    }


    @OnClick({R.id.tv_title_right, R.id.layout_info})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_title_right:
                submit();
                break;
            case R.id.layout_info:
                layout();
                break;
        }
    }

    private void layout() {
        switch (type.getValue()) {
            case 2:
                ActivityUtils.startActivity(SelectSexActivity.class);
                break;
            case 4:
                ActivityUtils.startActivity(SelectPlandWindowActivity.class);
                break;
        }
    }

    /***
     * 完成
     */
    private void submit() {
        switch (type.getValue()) {
            case 1:
                String heigth = etInfo.getText().toString().trim();
                if (StringUtis.isEmpty(heigth) || heigth.length() == 0) {
                    showToast(getResources().getString(R.string.not_empty));
                    return;
                }
                String cm = tvMonad.getText().toString().trim();
                sendRx(heigth, cm);
                finish();
                break;
            case 2:
                String sex = tvMonad.getText().toString().toString();
                if (StringUtis.isEmpty(sex) || sex.length() == 0) {
                    showToast(getResources().getString(R.string.not_empty));
                    return;
                }
                sendRx(sex, null);
                finish();
                break;
            case 3:
                String str = etInfo.getText().toString().trim();
                if (StringUtis.isEmpty(str) || str.length() == 0) {
                    showToast(getResources().getString(R.string.not_empty));
                    return;
                }
                sendRx(str, null);
                finish();
                break;

            case 4:
                String address = tvMonad.getText().toString().toString();
                if (StringUtis.isEmpty(address) || address.length() == 0) {
                    showToast(getResources().getString(R.string.not_empty));
                    return;
                }
                sendRx(address, null);
                finish();
                break;
        }
    }

    private void sendRx(String arg1, String arg2) {
        Events<PersonInfoChangeData> events = new Events<PersonInfoChangeData>();
        events.what = Events.EventEnum.PERSON_INFO_CHANGE;
        PersonInfoChangeData personInfoChangeData = new PersonInfoChangeData();
        personInfoChangeData.setArg1(arg1);
        personInfoChangeData.setArg2(arg2);
        personInfoChangeData.setType(type);
        events.message = personInfoChangeData;
        RxBus.getInstance().send(events);
        finish();
    }

    public class PersonInfoChangeData implements Serializable {
        private String arg1;
        private String arg2;
        private ChangeType type;

        public String getArg1() {
            return arg1;
        }

        public void setArg1(String arg1) {
            this.arg1 = arg1;
        }

        public String getArg2() {
            return arg2;
        }

        public void setArg2(String arg2) {
            this.arg2 = arg2;
        }

        public ChangeType getType() {
            return type;
        }

        public void setType(ChangeType type) {
            this.type = type;
        }
    }

}
