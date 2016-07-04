package io.rong.imkit;

import android.content.Context;
import android.net.Uri;

import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * Created by jb on 2016/6/18.
 */
public class RongyunConfig {
    private String TAG = "RongyunConfig";
    private static final RongyunConfig instance = new RongyunConfig();
    private Context context;
    private boolean isConnectRonyun;

    public static RongyunConfig getInstance() {
        return instance;
    }

    public void init(Context context) {
        this.context = context;
        RongLogUtils.d(TAG, "-------------初始化融云-----------------");
        RongIM.init(context);
//        RongIM.getInstance().setMessageAttachedUserInfo(true);
    }

    public void connectRonyun(String token, final ConnectCallback callback) {
        if (isConnectRonyun)
            return;
        isConnectRonyun = true;
        RongLogUtils.d(TAG, "-------------连接融云-----------------");
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                if (callback != null)
                    callback.onTokenIncorrect();

            }

            @Override
            public void onSuccess(String s) {
                RongLogUtils.d(TAG, "-------------连接融云成功 id : " + s);
//                if (callback != null)
//                    callback.onSuccess(s);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                RongLogUtils.e(TAG, "------------->连接融云失败 code : " + errorCode);
//                if (callback != null)
////                    callback.onError(errorCode);
            }
        });
    }

    public void offline() {
        RongIM.getInstance().logout();
    }

    public interface ConnectCallback {
        void onSuccess(String s);
//
//        void onError(RongIMClient.ErrorCode errorCode);

        void onTokenIncorrect();
    }

    /**
     * 设置用户信息
     *
     * @param id
     * @param name
     * @param avater
     */
    public void setUserInfoCache(String id, String name, String avater) {

        RongIM.getInstance().refreshUserInfoCache(new UserInfo(id, name, Uri.parse(avater)));

    }

    /**
     * 设置用户信息
     *
     * @param id
     * @param name
     * @param avater
     */
    public void setCurrentUserInfo(String id, String name, String avater) {
        RongIM.getInstance().setCurrentUserInfo(new UserInfo(id, name, Uri.parse(avater)));

    }
    public void ss(){
        //扩展功能自定义
        InputProvider.ExtendProvider[] provider = {
                new ImageInputProvider(RongContext.getInstance()),//图片
                new CameraInputProvider(RongContext.getInstance()),//相机
                new LocationInputProvider(RongContext.getInstance()),//地理位置
//                new VoIPInputProvider(RongContext.getInstance()),// 语音通话
//                new ContactsProvider(RongContext.getInstance())//自定义通讯录
        };
        RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, provider);
    }
}
