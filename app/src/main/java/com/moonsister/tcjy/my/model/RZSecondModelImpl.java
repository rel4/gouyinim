package com.moonsister.tcjy.my.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.moonsister.tcjy.AppConstant;
import com.moonsister.tcjy.ServerApi;
import com.moonsister.tcjy.manager.aliyun.AliyunManager;
import com.moonsister.tcjy.bean.DynamicContent;
import com.moonsister.tcjy.bean.DefaultDataBean;
import com.moonsister.tcjy.center.presenter.DynamicPublishPresenterImpl;
import com.moonsister.tcjy.manager.UserInfoManager;
import com.moonsister.tcjy.utils.FilePathUtlis;
import com.moonsister.tcjy.utils.ImageUtils;
import com.moonsister.tcjy.utils.JsonUtils;
import com.moonsister.tcjy.utils.LogUtils;
import com.moonsister.tcjy.utils.StringUtis;

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
        ArrayList<DynamicContent> aliyunPtahs = new ArrayList<DynamicContent>();
        Observable.create(new Observable.OnSubscribe<ArrayList<DynamicContent>>() {
            @Override
            public void call(Subscriber<? super ArrayList<DynamicContent>> subscriber) {

                try {
                    for (int i = 0; i < pics.size(); i++) {
                        DynamicContent image = new DynamicContent();
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
                .subscribe(new Subscriber<ArrayList<DynamicContent>>() {
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
                    public void onNext(ArrayList<DynamicContent> s) {
                        String loadFile = "";
                        if (!StringUtis.isEmpty(avaterpath)) {
                            Bitmap avater = ImageUtils.compressImageSzie(BitmapFactory.decodeFile(avaterpath), 120, 120);
                            Bitmap bitmap = ImageUtils.compressImage(avater, 10);
                            loadFile = AliyunManager.getInstance().upLoadFiletFromByteArray(ImageUtils.getBitmapByte(bitmap), FilePathUtlis.FileType.JPG);
                        }
                        LogUtils.e(DynamicPublishPresenterImpl.class, "  onNext :　" + aliyunPtahs.toString());
                        String serialize = JsonUtils.serialize(s);
                        LogUtils.e(RZSecondModelImpl.this, "JsonUtils : " + serialize);
                        sendAllRz(address1, address2, height, sexid, nike, loadFile, serialize, listener);
                    }


                });
    }

    private void sendAllRz(String address1, String address2, String height, String sexid, String nike, String loadFile, String serialize, onLoadDateSingleListener listener) {

        ServerApi.getAppAPI().sendAllRzInfo(address1, address2, height, sexid, nike, loadFile, serialize, UserInfoManager.getInstance().getAuthcode(), AppConstant.CHANNEL_ID)
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
