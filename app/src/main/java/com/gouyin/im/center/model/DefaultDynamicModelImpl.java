package com.gouyin.im.center.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.gouyin.im.ServerApi;
import com.gouyin.im.aliyun.AliyunManager;
import com.gouyin.im.base.ImageObjoct;
import com.gouyin.im.bean.BaseBean;
import com.gouyin.im.center.presenter.DefaultDynamicPresenterImpl;
import com.gouyin.im.center.widget.DynamicSendActivity;
import com.gouyin.im.utils.FilePathUtlis;
import com.gouyin.im.utils.ImageUtils;
import com.gouyin.im.utils.JsonUtils;
import com.gouyin.im.utils.LogUtils;
import com.gouyin.im.utils.SDUtils;
import com.gouyin.im.utils.UserInfoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/6/23.
 */
public class DefaultDynamicModelImpl implements DefaultDynamicModel {
    @Override
    public void sendDynamicPics(DynamicSendActivity.DynamicType dynamicType, String content, List<String> srcdatas, String address, onLoadDateSingleListener listener) {
        LogUtils.e(DefaultDynamicModelImpl.this, "start upload");
        ArrayList<ImageObjoct> aliyunPtahs = new ArrayList<ImageObjoct>();
        boolean isHaveFuzz = dynamicType == DynamicSendActivity.DynamicType.RED_PACKET;
        Observable.create(new Observable.OnSubscribe<ArrayList<ImageObjoct>>() {
            @Override
            public void call(Subscriber<? super ArrayList<ImageObjoct>> subscriber) {
                try {
                    for (int i = 0; i < srcdatas.size(); i++) {
                        ImageObjoct image = new ImageObjoct();
                        String path = srcdatas.get(i);
                        Bitmap bitmap = ImageUtils.compressImage(BitmapFactory.decodeFile(path), 100);
//                        String loadFile = AliyunManager.getInstance().upLoadFile(path, FilePathUtlis.FileType.JPG);
                        String loadFile = AliyunManager.getInstance().upLoadFiletFromByteArray(ImageUtils.getBitmapByte(bitmap), FilePathUtlis.FileType.JPG);
                        if (isHaveFuzz) {
                            //压缩质量

                            //压缩大小
                            Bitmap bitmapsize = ImageUtils.compressImageSzie(bitmap, 30, 30);
                            Bitmap bitmap1 = ImageUtils.compressImage(bitmapsize, 50);
                            //模糊
                            Bitmap bitmapblur = ImageUtils.blurImageAmeliorate(bitmap1);
                            byte[] bitmapByte = ImageUtils.getBitmapByte(bitmapblur);
                            String fuzzyFile = AliyunManager.getInstance().upLoadFiletFromByteArray(bitmapByte, FilePathUtlis.FileType.JPG);
                            if (fuzzyFile == null)
                                fuzzyFile = "";
                            image.setS(fuzzyFile);
                        } else {
                            image.setS("");
                        }
                        image.setL(loadFile);
                        aliyunPtahs.add(image);
                    }

                    subscriber.onNext(aliyunPtahs);
                    String serialize = JsonUtils.serialize(aliyunPtahs);
                    LogUtils.e(DefaultDynamicModelImpl.this, "aliyunPtahs --JsonUtils : " + serialize);
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
                        LogUtils.e(DefaultDynamicModelImpl.this, "onError :　" + e.getMessage());
                        e.printStackTrace();
                        listener.onFailure(e.getMessage(), e);
                    }

                    @Override
                    public void onNext(ArrayList<ImageObjoct> s) {
                        LogUtils.e(DefaultDynamicPresenterImpl.class, "  onNext :　" + aliyunPtahs.toString());
                        String serialize = JsonUtils.serialize(s);
                        LogUtils.e(DefaultDynamicModelImpl.this, "JsonUtils : " + serialize);
                        sendAllDynamic(dynamicType, content, serialize, address, listener);
                    }
                });
    }

    private void sendAllDynamic(DynamicSendActivity.DynamicType dynamicType, String content, String json, String address, onLoadDateSingleListener listener) {
        String authcode = UserInfoUtils.getAuthcode();
        ServerApi.getAppAPI().sendAllDefaultDynamic(dynamicType.getValue(), content, json, "", address, authcode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e.getMessage(), e);
                    }

                    @Override
                    public void onNext(BaseBean bean) {
                        LogUtils.e(DefaultDynamicModelImpl.class, "onNext BaseBean : " + bean.toString());
                        listener.onSuccess(bean, DataType.DATA_ZERO);
                    }
                });

    }

}
