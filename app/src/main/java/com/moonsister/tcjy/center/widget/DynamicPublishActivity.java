package com.moonsister.tcjy.center.widget;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.moonsister.tcjy.R;
import com.moonsister.tcjy.bean.model.BaseFragmentActivity;
import com.moonsister.tcjy.center.presenter.DynamicPublishPresenter;
import com.moonsister.tcjy.center.presenter.DynamicPublishPresenterImpl;
import com.moonsister.tcjy.center.view.DefaultDynamicView;
import com.moonsister.tcjy.manager.GaodeManager;
import com.moonsister.tcjy.utils.StringUtis;
import com.moonsister.tcjy.utils.UIUtils;

import java.util.List;

/**
 * Created by jb on 2016/8/8.
 */
public class DynamicPublishActivity extends BaseFragmentActivity implements View.OnClickListener, DefaultDynamicView {
    private TextView tv_title_right;
    private DynamicPublishFragment dyf;
    private DynamicPublishPresenter presenter;

    @Override
    protected String initTitleName() {
        return super.initTitleName();
    }

    @Override
    protected Fragment initFragment() {
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_title_right.setText(UIUtils.getStringRes(R.string.publish));
        dyf = DynamicPublishFragment.newInstance();
        tv_title_right.setOnClickListener(this);
        presenter = new DynamicPublishPresenterImpl();
        presenter.attachView(this);
        return DynamicPublishFragment.newInstance();
    }

    @Override
    public boolean isBaseonActivityResult() {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (dyf == null)
            return;


        List<String> dynamicContent = dyf.getDynamicContent();
        if (dynamicContent == null || dynamicContent.size() == 0) {
            showToast(UIUtils.getStringRes(R.string.dynamic_content_is_empty));
            return;
        }
        List<String> lables = dyf.getLables();
        if (lables == null || lables.size() == 0) {
            showToast(UIUtils.getStringRes(R.string.lable_is_empty));
            return;
        }
        DynamicContentFragment.DynamicType dynamicType = dyf.getDynamicType();
        if (dynamicType == DynamicContentFragment.DynamicType.PIC && dynamicContent.size() < 6) {
            showToast(UIUtils.getStringRes(R.string.dynamic_pic_not_more_6));
            return;
        }
        String txtContent = dyf.getTXTContent();
        if (StringUtis.isEmpty(txtContent)) {
            showToast(UIUtils.getStringRes(R.string.input_not_empty));
            return;
        }
        boolean charge = dyf.isCharge();
        DynamicSendActivity.DynamicType type = null;
        if (dynamicType == DynamicContentFragment.DynamicType.PIC) {
            if (charge)
                type = DynamicSendActivity.DynamicType.CHARGE_PIC;
            else {
                type = DynamicSendActivity.DynamicType.PIC;
            }
        } else if (dynamicType == DynamicContentFragment.DynamicType.VOICE) {
            if (charge) {
                type = DynamicSendActivity.DynamicType.CHARGE_VOICE;
            } else
                type = DynamicSendActivity.DynamicType.VOICE;
        } else if (dynamicType == DynamicContentFragment.DynamicType.VIDEO) {
            if (charge)
                type = DynamicSendActivity.DynamicType.CHARGE_VIDEO;
            else
                type = DynamicSendActivity.DynamicType.CHARGE_VIDEO;
        }
        if (type == null) {
            showToast(UIUtils.getStringRes(R.string.publish_failure));
            return;
        }
        String adress = "";
        if (dyf.isShowAdress())
            adress = GaodeManager.getInstance().getStringAdress();
        presenter.sendDynamic(type, txtContent, dynamicContent, adress);
    }

    @Override
    public void finishPage() {
        finish();
    }

    @Override
    public void showLoading() {
//        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
    }

    @Override
    public void transfePageMsg(String msg) {
        showToast(msg);
    }
}
