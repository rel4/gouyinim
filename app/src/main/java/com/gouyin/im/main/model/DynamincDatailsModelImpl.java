package com.gouyin.im.main.model;

import android.os.SystemClock;

import com.gouyin.im.base.onLoadDateListener;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by pc on 2016/6/8.
 */
public class DynamincDatailsModelImpl implements DynamincDatailsModel {


    @Override
    public void loadData(final onLoadDateListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                UIUtils.onRunMainThred(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<BaseBean> beans = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            BaseBean baseBean = new BaseBean();
                            beans.add(baseBean);
                        }
                        listener.onSuccess(beans);
                    }
                });
            }
        }).start();
    }
}
