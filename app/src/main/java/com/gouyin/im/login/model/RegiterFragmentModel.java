package com.gouyin.im.login.model;

import com.gouyin.im.base.BaseIModel;

/**
 * Created by pc on 2016/6/14.
 */
public interface RegiterFragmentModel extends BaseIModel {
    void loadSubmit(String phoneNumber,String code,onLoadSubmitListenter listenter);

     void loadSecurity(String phoneMunber,onLoadDateListener listener);

    public interface onLoadSubmitListenter<T> {

        void onSubmitSuccess(T t);


        void onSubmitFailure(String msg, Exception e);
    }

}
