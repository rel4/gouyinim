package com.moonsister.tcjy.center.model;

import android.graphics.Bitmap;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.moonsister.tcjy.AppConstant;
import com.moonsister.tcjy.ServerApi;
import com.moonsister.tcjy.manager.aliyun.AliyunManager;
import com.moonsister.tcjy.bean.DynamicContent;
import com.moonsister.tcjy.bean.BaseBean;
import com.moonsister.tcjy.center.presenter.DynamicPublishPresenterImpl;
import com.moonsister.tcjy.center.widget.DynamicSendActivity;
import com.moonsister.tcjy.manager.UserInfoManager;
import com.moonsister.tcjy.utils.FastBlur;
import com.moonsister.tcjy.utils.FilePathUtlis;
import com.moonsister.tcjy.utils.ImageUtils;
import com.moonsister.tcjy.utils.JsonUtils;
import com.moonsister.tcjy.utils.LogUtils;
import com.moonsister.tcjy.utils.ObservableUtils;
import com.moonsister.tcjy.utils.VideoUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/6/23.
 */
public class DynamicPublishModelImpl implements DynamicPublishModel {
    @Override
    public void sendDynamicPics(DynamicSendActivity.DynamicType dynamicType, String content, List<String> srcdatas, String address, onLoadDateSingleListener listener) {
        LogUtils.e(DynamicPublishModelImpl.this, "start upload");
//        ArrayList<DynamicContent> aliyunPtahs = new ArrayList<DynamicContent>();

        Observable.create(new Observable.OnSubscribe<ArrayList<DynamicContent>>() {
            @Override
            public void call(Subscriber<? super ArrayList<DynamicContent>> subscriber) {
                upLoadFile(dynamicType, srcdatas, subscriber);
//                if (dynamicType == DynamicSendActivity.DynamicType.VIDEO || dynamicType == DynamicSendActivity.DynamicType.CHARGE_VIDEO) {
//                    if (srcdatas != null && srcdatas.size() == 2) {
//
//                        try {
//                            //视频
//                            String vidoPath = AliyunManager.getInstance().upLoadFile(srcdatas.get(0), FilePathUtlis.FileType.MP4);
//                            //图片
//                            Bitmap size = ImageUtils.compressImageWithPathSzie(srcdatas.get(1), 200, 160);
//                            //压缩大小
//                            Bitmap bitmap = ImageUtils.compressImage(size, 100);
//                            String loadFile = AliyunManager.getInstance().upLoadFiletFromByteArray(ImageUtils.getBitmapByte(bitmap), FilePathUtlis.FileType.JPG);
//                            DynamicContent image = new DynamicContent();
//                            image.setS(loadFile);
//                            image.setV(vidoPath);
//                            image.setL(loadFile);
//                            aliyunPtahs.add(image);
//                        } catch (ClientException e) {
//                            e.printStackTrace();
//                        } catch (ServiceException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                    subscriber.onNext(aliyunPtahs);
//                    return;
//                }
//                boolean isHaveFuzz = dynamicType == DynamicSendActivity.DynamicType.CHARGE_PIC;
//                try {
//                    for (int i = 0; i < srcdatas.size(); i++) {
//                        DynamicContent image = new DynamicContent();
//                        String path = srcdatas.get(i);
//                        Bitmap size = ImageUtils.compressImageWithPathSzie(path, 800, 600);
//                        Bitmap bitmap = ImageUtils.compressImage(size, 1000);
////                        String loadFile = AliyunManager.getInstance().upLoadFile(path, FilePathUtlis.FileType.JPG);
//                        String loadFile = AliyunManager.getInstance().upLoadFiletFromByteArray(ImageUtils.getBitmapByte(bitmap), FilePathUtlis.FileType.JPG);
//                        if (isHaveFuzz) {
//                            //压缩质量
//
//                            //宽高压缩
//                            Bitmap bitmapsize = ImageUtils.compressImageSzie(bitmap, 200, 200);
//                            //质量压缩
//                            Bitmap bitmap1 = ImageUtils.compressImage(bitmapsize, 50);
//                            //模糊
////                            Bitmap bitmapblur = ImageUtils.blurImageAmeliorate(bitmap1,30,true);
//                            Bitmap bitmap2 = FastBlur.doBlur(bitmap1, 20, true);
//                            byte[] bitmapByte = ImageUtils.getBitmapByte(bitmap2);
//                            String fuzzyFile = AliyunManager.getInstance().upLoadFiletFromByteArray(bitmapByte, FilePathUtlis.FileType.JPG);
//                            if (fuzzyFile == null)
//                                fuzzyFile = "";
//                            image.setS(fuzzyFile);
//                        } else {
//                            image.setS("");
//                        }
//                        image.setL(loadFile);
//                        aliyunPtahs.add(image);
//                    }
//
//                    subscriber.onNext(aliyunPtahs);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }).observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ArrayList<DynamicContent>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(DynamicPublishModelImpl.this, "onError :　" + e.getMessage());
                        e.printStackTrace();
                        listener.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<DynamicContent> s) {
                        LogUtils.e(DynamicPublishPresenterImpl.class, "  onNext :　" + s.toString());
                        String serialize = JsonUtils.serialize(s);
                        LogUtils.e(DynamicPublishModelImpl.this, "JsonUtils : " + serialize);
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

    private void upLoadFile(DynamicSendActivity.DynamicType dynamicType, List<String> datas, Subscriber<? super ArrayList<DynamicContent>> subscriber) {
        try {
            ArrayList<DynamicContent> aliyunPtahs = new ArrayList<DynamicContent>();
            switch (dynamicType) {
                case PIC:
                    upPic(datas, aliyunPtahs, false);
                    break;
                case CHARGE_PIC:
                    upPic(datas, aliyunPtahs, true);
                    break;
                case VIDEO:
                    upLoadVideo(datas.get(0), aliyunPtahs, false);
                    break;
                case CHARGE_VIDEO:
                    upLoadVideo(datas.get(0), aliyunPtahs, true);
                    break;
                case VOICE:
                    upLoadVoice(datas.get(0), aliyunPtahs, false);
                    break;
                case CHARGE_VOICE:
                    upLoadVoice(datas.get(0), aliyunPtahs, true);
                    break;

            }
            subscriber.onNext(aliyunPtahs);
        } catch (ServiceException e) {
            e.printStackTrace();
            subscriber.onError(e);
        } catch (ClientException e) {
            e.printStackTrace();
            subscriber.onError(e);
        }


    }

    /**
     * 上传图片
     *
     * @param pics
     * @param aliyunPtahs
     * @param isCharge
     */
    private void upPic(List<String> pics, ArrayList<DynamicContent> aliyunPtahs, boolean isCharge) {
        for (int i = 0; i < pics.size(); i++) {
            DynamicContent content = new DynamicContent();
            String path = pics.get(i);
            Bitmap size = ImageUtils.compressImageWithPathSzie(path, 800, 600);
            Bitmap bitmap = ImageUtils.compressImage(size, 1000);
//                        String loadFile = AliyunManager.getInstance().upLoadFile(path, FilePathUtlis.FileType.JPG);
            String loadFile = AliyunManager.getInstance().upLoadFiletFromByteArray(ImageUtils.getBitmapByte(bitmap), FilePathUtlis.FileType.JPG);
            if (isCharge) {
                //压缩质量

                //宽高压缩
                Bitmap bitmapsize = ImageUtils.compressImageSzie(bitmap, 200, 200);
                //质量压缩
                Bitmap bitmap1 = ImageUtils.compressImage(bitmapsize, 50);
                //模糊
                Bitmap bitmap2 = FastBlur.doBlur(bitmap1, 20, true);
                byte[] bitmapByte = ImageUtils.getBitmapByte(bitmap2);
                String fuzzyFile = AliyunManager.getInstance().upLoadFiletFromByteArray(bitmapByte, FilePathUtlis.FileType.JPG);
                if (fuzzyFile == null)
                    fuzzyFile = "";
                content.setS(fuzzyFile);
            } else {
                content.setS("");
            }
            content.setL(loadFile);
            aliyunPtahs.add(content);
        }
    }

    public void upLoadVideo(String videoPath, ArrayList<DynamicContent> aliyunPtahs, boolean isCharge) throws ClientException, ServiceException {


        //视频
        String vidoPath = AliyunManager.getInstance().upLoadFile(videoPath, FilePathUtlis.FileType.MP4);
        //图片
        String videoThumbnail = VideoUtils.getInstance().getVideoThumbnail(videoPath);
        Bitmap size = ImageUtils.compressImageWithPathSzie(videoThumbnail, 200, 160);
        //压缩大小
        Bitmap bitmap = ImageUtils.compressImage(size, 100);
        String loadFile = AliyunManager.getInstance().upLoadFiletFromByteArray(ImageUtils.getBitmapByte(bitmap), FilePathUtlis.FileType.JPG);
        DynamicContent content = new DynamicContent();
        content.setV(vidoPath);
        content.setL(loadFile);
        if (isCharge) {
            //压缩质量

            //宽高压缩
            Bitmap bitmapsize = ImageUtils.compressImageSzie(bitmap, 200, 200);
            //质量压缩
            Bitmap bitmap1 = ImageUtils.compressImage(bitmapsize, 50);
            //模糊
            Bitmap bitmap2 = FastBlur.doBlur(bitmap1, 20, true);
            byte[] bitmapByte = ImageUtils.getBitmapByte(bitmap2);
            String fuzzyFile = AliyunManager.getInstance().upLoadFiletFromByteArray(bitmapByte, FilePathUtlis.FileType.JPG);

            if (fuzzyFile == null)
                fuzzyFile = "";
            content.setS(fuzzyFile);
        } else {
            content.setS("");
        }

        aliyunPtahs.add(content);
    }

    public void upLoadVoice(String videoPath, ArrayList<DynamicContent> aliyunPtahs, boolean isCharge) throws ClientException, ServiceException {
        String s = AliyunManager.getInstance().upLoadFile(videoPath, FilePathUtlis.FileType.AMR);
        DynamicContent content = new DynamicContent();
        content.setV(s);
        aliyunPtahs.add(content);
    }
}
