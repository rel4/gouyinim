package com.gouyin.im.my.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.gouyin.im.ServerApi;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.bean.UserInfoChangeBean;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.manager.aliyun.AliyunManager;
import com.gouyin.im.utils.FilePathUtlis;
import com.gouyin.im.utils.ImageUtils;
import com.gouyin.im.utils.ObservableUtils;
import com.gouyin.im.utils.StringUtis;

import java.io.File;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/7/11.
 */
public class UserInfoChangeActivityModelImpl implements UserInfoChangeActivityModel {
    @Override
    public void loadBasiData(onLoadDateSingleListener listener) {
        Observable<UserInfoChangeBean> observable = ServerApi.getAppAPI().getUserInfoBasic(UserInfoManager.getInstance().getMemoryPersonInfoDetail().getId());
        ObservableUtils.parser(observable, new ObservableUtils.Callback<UserInfoChangeBean>() {
            @Override
            public void onSuccess(UserInfoChangeBean bean) {
                listener.onSuccess(bean, DataType.DATA_ZERO);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }

    @Override
    public void submit(UserInfoChangeBean.DataBean dataBean, onLoadDateSingleListener listener) {

        String face = dataBean.getFace();
        File file = null;
        if (!StringUtis.isEmpty(face)) {
            file = new File(face);
        }
        if (StringUtis.isEmpty(face) || file == null || !file.exists()) {
            dataBean.setFace("");
            upload(dataBean, listener);
        } else {
            Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {

                    Bitmap avater = ImageUtils.compressImageSzie(BitmapFactory.decodeFile(face), 120, 120);
                    Bitmap bitmap = ImageUtils.compressImage(avater, 10);
                    String s = AliyunManager.getInstance().upLoadFiletFromByteArray(ImageUtils.getBitmapByte(bitmap), FilePathUtlis.FileType.JPG);
                    subscriber.onNext(s);

                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(String s) {
                            dataBean.setFace(s);
                            upload(dataBean, listener);
                        }
                    });
        }

    }

    private void upload(UserInfoChangeBean.DataBean dataBean, onLoadDateSingleListener listener) {

        String residence = dataBean.getResidence();
        String province = "";
        String city = "";
        if (residence.contains(".")) {
            String[] split = residence.split("\\.");
            if (split.length >= 2) {
                province = split[0];
                city = split[1];
            }
        }
        Observable<DefaultDataBean> changeInfo = ServerApi.getAppAPI().getChangeInfo(
                StringUtis.formatAPI(dataBean.getFace()),
                StringUtis.formatAPI(dataBean.getBirthday()),
                StringUtis.formatAPI(dataBean.getNickname()),
                StringUtis.formatAPI(dataBean.getHeight()),
                StringUtis.formatAPI(dataBean.getProfession()),
                StringUtis.formatAPI(province),
                StringUtis.formatAPI(city),
                StringUtis.formatAPI(dataBean.getSignature()),
                StringUtis.formatAPI(dataBean.getWeight()),
                StringUtis.formatAPI(dataBean.getSex() + ""),
                StringUtis.formatAPI(dataBean.getDegree()),
                StringUtis.formatAPI(dataBean.getSelf_intro()),
                StringUtis.formatAPI(UserInfoManager.getInstance().getAuthcode()));
        ObservableUtils.parser(changeInfo, new ObservableUtils.Callback<DefaultDataBean>() {
            @Override
            public void onSuccess(DefaultDataBean bean) {
                listener.onSuccess(bean, DataType.DATA_ONE);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });
    }
}
