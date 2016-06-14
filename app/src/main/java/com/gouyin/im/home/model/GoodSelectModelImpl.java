package com.gouyin.im.home.model;

import android.os.SystemClock;

import com.gouyin.im.base.onLoadDateListener;
import com.gouyin.im.bean.GoodSelectBaen;

import com.gouyin.im.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by pc on 2016/6/3.
 */
public class GoodSelectModelImpl  implements GoodSelectModel {
    private int index;
    @Override
    public void loadGoodSelectDate(final onLoadDateListener<List<GoodSelectBaen>> listener) {
//        String url = "";
//        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
//            @Override
//            public void onSuccess(String response) {
////                List<GoodSelectBaen> iamgeBeanList = ImageJsonUtils.readJsonImageBeans(response);
//                listener.onSuccess(null);
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                listener.onFailure("load image list failure.", e);
//            }
//        };
//        OkHttpUtils.get(url, loadNewsCallback);
        new Thread(new Runnable() {
            @Override
            public void run() {
//                SystemClock.sleep(5000);
                final List<GoodSelectBaen> goodSelectBaens = new ArrayList<GoodSelectBaen>();
                for (int i = 0; i < 15; i++) {
                    index =index+i;

                    GoodSelectBaen goodSelectBaen = new GoodSelectBaen();
                    goodSelectBaen.setMsg("item : " + index);
                    goodSelectBaens.add(goodSelectBaen);

                }
                UIUtils.onRunMainThred(new Runnable() {
                    @Override
                    public void run() {
                        listener.onSuccess(goodSelectBaens);
                    }
                });

            }
        }).start();
    }

}
