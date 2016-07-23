package com.moonsister.tcjy.center.model;

import android.graphics.Bitmap;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.moonsister.tcjy.AppConstant;
import com.moonsister.tcjy.ServerApi;
import com.moonsister.tcjy.manager.aliyun.AliyunManager;
import com.moonsister.tcjy.bean.ImageObjoct;
import com.moonsister.tcjy.bean.BaseBean;
import com.moonsister.tcjy.center.presenter.DefaultDynamicPresenterImpl;
import com.moonsister.tcjy.center.widget.DynamicSendActivity;
import com.moonsister.tcjy.manager.UserInfoManager;
import com.moonsister.tcjy.utils.FastBlur;
import com.moonsister.tcjy.utils.FilePathUtlis;
import com.moonsister.tcjy.utils.ImageUtils;
import com.moonsister.tcjy.utils.JsonUtils;
import com.moonsister.tcjy.utils.LogUtils;
import com.moonsister.tcjy.utils.ObservableUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/6/23.
 */
public class DefaultDynamicModelImpl implements DefaultDynamicModel {
    @Override
    public void sendDynamicPics(DynamicSendActivity.DynamicType dynamicType, String content, List<String> srcdatas, String address, onLoadDateSingleListener listener) {
        LogUtils.e(DefaultDynamicModelImpl.this, "start upload");
        ArrayList<ImageObjoct> aliyunPtahs = new ArrayList<ImageObjoct>();

        Observable.create(new Observable.OnSubscribe<ArrayList<ImageObjoct>>() {
            @Override
            public void call(Subscriber<? super ArrayList<ImageObjoct>> subscriber) {
                if (dynamicType == DynamicSendActivity.DynamicType.video) {
                    if (srcdatas != null && srcdatas.size() == 2) {

                        try {
                            //视频
                            String vidoPath = AliyunManager.getInstance().upLoadFile(srcdatas.get(0), FilePathUtlis.FileType.MP4);
                            //图片
                            Bitmap size = ImageUtils.compressImageWithPathSzie(srcdatas.get(1), 200, 160);
                            //压缩大小
                            Bitmap bitmap = ImageUtils.compressImage(size, 100);
                            String loadFile = AliyunManager.getInstance().upLoadFiletFromByteArray(ImageUtils.getBitmapByte(bitmap), FilePathUtlis.FileType.JPG);
                            ImageObjoct image = new ImageObjoct();
                            image.setS(loadFile);
                            image.setV(vidoPath);
                            image.setL("");
                            aliyunPtahs.add(image);
                        } catch (ClientException e) {
                            e.printStackTrace();
                        } catch (ServiceException e) {
                            e.printStackTrace();
                        }

                    }
                    subscriber.onNext(aliyunPtahs);
                    return;
                }
                boolean isHaveFuzz = dynamicType == DynamicSendActivity.DynamicType.RED_PACKET;
                try {
                    for (int i = 0; i < srcdatas.size(); i++) {
                        ImageObjoct image = new ImageObjoct();
                        String path = srcdatas.get(i);
                        Bitmap size = ImageUtils.compressImageWithPathSzie(path, 800, 600);
                        Bitmap bitmap = ImageUtils.compressImage(size, 1000);
//                        String loadFile = AliyunManager.getInstance().upLoadFile(path, FilePathUtlis.FileType.JPG);
                        String loadFile = AliyunManager.getInstance().upLoadFiletFromByteArray(ImageUtils.getBitmapByte(bitmap), FilePathUtlis.FileType.JPG);
                        if (isHaveFuzz) {
                            //压缩质量

                            //宽高压缩
                            Bitmap bitmapsize = ImageUtils.compressImageSzie(bitmap, 200, 200);
                            //质量压缩
                            Bitmap bitmap1 = ImageUtils.compressImage(bitmapsize, 50);
                            //模糊
//                            Bitmap bitmapblur = ImageUtils.blurImageAmeliorate(bitmap1,30,true);
                            Bitmap bitmap2 = FastBlur.doBlur(bitmap1, 20, true);
                            byte[] bitmapByte = ImageUtils.getBitmapByte(bitmap2);
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
                } catch (Exception e) {
                    e.printStackTrace();
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
                        listener.onFailure(e.getMessage());
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
        String authcode = UserInfoManager.getInstance().getAuthcode();
        Observable<BaseBean> baseBeanObservable = ServerApi.getAppAPI().sendAllDefaultDynamic(dynamicType.getValue(), content, json, address, authcode, AppConstant.CHANNEL_ID);

        ObservableUtils.parser(baseBeanObservable, new ObservableUtils.Callback<BaseBean>() {
            @Override
            public void onSuccess(BaseBean bean) {
                listener.onSuccess(bean, DataType.DATA_ZERO);
            }

            @Override
            public void onFailure(String msg) {
                listener.onFailure(msg);
            }
        });

    }

}
