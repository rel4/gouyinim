package io.rong.imkit;

import android.content.Context;

import io.rong.imlib.RongIMClient;

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
//        void onSuccess(String s);
//
//        void onError(RongIMClient.ErrorCode errorCode);

        void onTokenIncorrect();
    }

}
