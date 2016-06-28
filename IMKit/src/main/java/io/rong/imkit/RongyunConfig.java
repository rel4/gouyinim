package io.rong.imkit;

import android.content.Context;

import io.rong.imlib.RongIMClient;

/**
 * Created by jb on 2016/6/18.
 */
public class RongyunConfig {
    private static final RongyunConfig instance = new RongyunConfig();
    private Context context;
    private boolean isConnectRonyun;

    public static RongyunConfig getInstance() {
        return instance;
    }

    public void init(Context context) {
        this.context = context;
        RongLogUtils.e(this, "-------------初始化融云-----------------");
        RongIM.init(context);
    }

    public void connectRonyun(String token, final ConnectCallback callback) {
        if (isConnectRonyun)
            return;
        isConnectRonyun = true;
        RongLogUtils.e(this, "-------------连接融云-----------------");
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                if (callback != null)
                    callback.onTokenIncorrect();

            }

            @Override
            public void onSuccess(String s) {
                if (callback != null)
                    callback.onSuccess(s);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

                if (callback != null)
                    callback.onError(errorCode);
            }
        });
    }

    public void offline() {
        RongIM.getInstance().logout();
    }

    public interface ConnectCallback {
        void onSuccess(String s);

        void onError(RongIMClient.ErrorCode errorCode);

        void onTokenIncorrect();
    }

}
