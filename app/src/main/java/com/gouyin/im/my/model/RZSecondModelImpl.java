package com.gouyin.im.my.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.gouyin.im.ServerApi;
import com.gouyin.im.manager.aliyun.AliyunManager;
import com.gouyin.im.bean.ImageObjoct;
import com.gouyin.im.bean.DefaultDataBean;
import com.gouyin.im.center.presenter.DefaultDynamicPresenterImpl;
import com.gouyin.im.manager.UserInfoManager;
import com.gouyin.im.utils.FilePathUtlis;
import com.gouyin.im.utils.ImageUtils;
import com.gouyin.im.utils.JsonUtils;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.StringUtis;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/6/30.
 */
public class RZSecondModelImpl implements RZSecondModel {

    @Override
    public void submit(String address1, String address2, String height, String sexid, String nike, String avaterpath, ArrayList<String> pics, onLoadDateSingleListener listener) {
        LogUtils.e(RZSecondModelImpl.this, "start upload");
        ArrayList<ImageObjoct> aliyunPtahs = new ArrayList<ImageObjoct>();
        Observable.create(new Observable.OnSubscribe<ArrayList<ImageObjoct>>() {
            @Override
            public void call(Subscriber<? super ArrayList<ImageObjoct>> subscriber) {

                try {
                    for (int i = 0; i < pics.size(); i++) {
                        ImageObjoct image = new ImageObjoct();
                        String path = pics.get(i);
                        Bitmap size = ImageUtils.compressImageWithPathSzie(path, 800, 600);
                        Bitmap bitmap = ImageUtils.compressImage(size, 1000);
                        String loadFile = AliyunManager.getInstance().upLoadFiletFromByteArray(ImageUtils.getBitmapByte(bitmap), FilePathUtlis.FileType.JPG);
                        image.setL(loadFile);
                        aliyunPtahs.add(image);
                    }

                    subscriber.onNext(aliyunPtahs);
                } catch (Exception e) {

                }
            }
        }).observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ArrayList<ImageObjoct>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(RZSecondModelImpl.this, "onError :　" + e.getMessage());
                        e.printStackTrace();
                        listener.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<ImageObjoct> s) {
                        String loadFile = "";
                        if (!StringUtis.isEmpty(avaterpath)) {
                            Bitmap avater = ImageUtils.compressImageSzie(BitmapFactory.decodeFile(avaterpath), 120, 120);
                            Bitmap bitmap = ImageUtils.compressImage(avater, 10);
                            loadFile = AliyunManager.getInstance().upLoadFiletFromByteArray(ImageUtils.getBitmapByte(bitmap), FilePathUtlis.FileType.JPG);
                        }
                        LogUtils.e(DefaultDynamicPresenterImpl.class, "  onNext :　" + aliyunPtahs.toString());
                        String serialize = JsonUtils.serialize(s);
                        LogUtils.e(RZSecondModelImpl.this, "JsonUtils : " + serialize);
                        sendAllRz(address1, address2, height, sexid, nike, loadFile, serialize, listener);
                    }


                });
    }

    private void sendAllRz(String address1, String address2, String height, String sexid, String nike, String loadFile, String serialize, onLoadDateSingleListener listener) {

        ServerApi.getAppAPI().sendAllRzInfo(address1, address2, height, sexid, nike, loadFile, serialize, UserInfoManager.getInstance().getAuthcode())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DefaultDataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(DefaultDataBean defaultDataBean) {
                        listener.onSuccess(defaultDataBean, DataType.DATA_ZERO);
                    }
                });


    }


}
