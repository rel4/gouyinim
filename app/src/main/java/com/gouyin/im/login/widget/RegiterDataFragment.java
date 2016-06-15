package com.gouyin.im.login.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gouyin.im.ImageServerApi;
import com.gouyin.im.R;
import com.gouyin.im.base.BaseFragment;
import com.gouyin.im.event.Events;
import com.gouyin.im.event.RxBus;
import com.gouyin.im.login.presenter.RegiterDataFragmentPresenter;
import com.gouyin.im.login.presenter.RegiterDataFragmentPresenterImpl;
import com.gouyin.im.login.view.RegiterDataFragmentView;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.UIUtils;
import com.trello.rxlifecycle.FragmentEvent;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pc on 2016/6/13.
 */
public class RegiterDataFragment extends BaseFragment implements RegiterDataFragmentView {
    @Bind(R.id.add_icom)
    ImageView addIcom;
    @Bind(R.id.tv_boy)
    TextView tvBoy;
    @Bind(R.id.girls)
    TextView girls;
    @Bind(R.id.tv_pwd)
    TextView tvPwd;
    @Bind(R.id.edit_password)
    EditText editPassword;
    private RegiterDataFragmentPresenter presenter;
    private String sex = "1";

    public static RegiterDataFragment newInstance() {
        return new RegiterDataFragment();
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        presenter = new RegiterDataFragmentPresenterImpl();
        presenter.attachView(this);
        return UIUtils.inflateLayout(R.layout.fragment_regiter_data, container);
    }

    @Override
    protected void initData() {
        tvBoy.setSelected(true);
    }

    @OnClick({R.id.add_icom, R.id.tv_boy, R.id.girls, R.id.id_tv_load})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_icom:

                UIUtils.startActivity(SelectPicPopupActivity.class);
                RxBus.with(this)
                        .setEvent(Events.EventEnum.GET_PHOTO)
                        .setEndEvent(FragmentEvent.DESTROY)
                        .onNext((events) -> {
                            String message = (String) events.message;
                            LogUtils.e(RegiterDataFragment.class, "pic_path : " + message);
                            ImageServerApi.showURLImage(addIcom, message);
                        }).create();

                break;
            case R.id.tv_boy:
                tvBoy.setSelected(true);
                girls.setSelected(false);
                sex = "1";
                break;
            case R.id.girls:
                sex = "2";
                tvBoy.setSelected(false);
                girls.setSelected(true);
                break;
            case R.id.id_tv_load:
                String pwd = editPassword.getText().toString().trim();
                if (pwd == null || pwd.length() == 0) {
                    showToast(resources.getString(R.string.password) + resources.getString(R.string.not_empty));
                    return;
                }
                if (pwd .length()>16 || pwd.length() < 0) {
                    showToast(resources.getString(R.string.password) +resources.getString(R.string.framat)+resources.getString(R.string.error));
                    return;
                }
                presenter.login("", sex,pwd,((LoginMainActivity)getActivity()).regiterCode );
                break;
        }
    }


    @Override
    public void navigationNext() {
        getActivity().finish();
    }

    @Override
    public void requestFailed(String reason) {
        showToast(reason);
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
    }
}
