package com.moonsister.tcjy.my.persenter;

import com.moonsister.tcjy.AppConstant;
import com.moonsister.tcjy.base.BaseIModel;
import com.moonsister.tcjy.bean.DefaultDataBean;
import com.moonsister.tcjy.bean.UserInfoChangeBean;
import com.moonsister.tcjy.my.model.UserInfoChangeActivityModel;
import com.moonsister.tcjy.my.model.UserInfoChangeActivityModelImpl;
import com.moonsister.tcjy.my.view.UserInfoChangeActivityView;
import com.moonsister.tcjy.utils.StringUtis;
import com.moonsister.tcjy.utils.UIUtils;

/**
 * Created by jb on 2016/7/11.
 */
public class UserInfoChangeActivityPresenterImpl implements UserInfoChangeActivityPresenter, BaseIModel.onLoadDateSingleListener {
    private UserInfoChangeActivityView view;
    private UserInfoChangeActivityModel model;

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(UserInfoChangeActivityView userInfoChangeActivityView) {
        this.view = userInfoChangeActivityView;
        model = new UserInfoChangeActivityModelImpl();
    }

    @Override
    public void loadbasicData() {
        view.showLoading();
        model.loadBasiData(this);
    }

    @Override
    public void submit(UserInfoChangeBean.DataBean dataBean) {
        view.showLoading();
        model.submit(dataBean,this);
    }

    @Override
    public void onSuccess(Object obj, BaseIModel.DataType dataType) {
        switch (dataType){
            case DATA_ZERO:
                view.setUserBasic((UserInfoChangeBean) obj);
                break;
            case DATA_ONE:
                DefaultDataBean bean = (DefaultDataBean) obj;
                if (bean!=null){
                    if (StringUtis.equals(bean.getCode(), AppConstant.code_request_success)){
                        UIUtils.sendDelayedOneMillis(new Runnable() {
                            @Override
                            public void run() {
                                view.pageFinish();
                            }
                        });
                    }
                    view.transfePageMsg(bean.getMsg());
                }else {}
            break;
        }
        view.hideLoading();

    }

    @Override
    public void onFailure(String msg) {
        view.hideLoading();
        view.transfePageMsg(msg);
    }
}
