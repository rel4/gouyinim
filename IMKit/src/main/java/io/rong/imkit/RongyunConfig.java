package io.rong.imkit;

import android.content.Context;
import android.net.Uri;

import io.rong.imkit.provider.RedPacketMessage;
import io.rong.imkit.provider.RedPacketMessageItemProvider;
import io.rong.imkit.provider.RedPacketProvider;
import io.rong.imkit.userInfoCache.RongUserInfoManager;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;

/**
 * Created by jb on 2016/6/18.
 */
public class RongyunConfig {
    private String TAG = "RongyunConfig";
    private static final RongyunConfig instance = new RongyunConfig();
    private Context context;
    private boolean isConnectRonyun;

    public String getUserName(String mTargetId) {
        UserInfo userInfo = RongUserInfoManager.getInstance().getUserInfo(mTargetId);
        if (userInfo != null)
            return userInfo.getName();
        return "";
    }

    public interface ConnectCallback {
        void onSuccess(String s);

        void onTokenIncorrect();
    }

    public static RongyunConfig getInstance() {
        return instance;
    }

    public void init(Context context) {
//        this.context = context;
//        RongLogUtils.d(TAG, "-------------初始化融云-----------------");
        RongIM.init(context);
        RongIM.registerMessageType(RedPacketMessage.class);
        RongIM.getInstance().registerMessageTemplate(new RedPacketMessageItemProvider());
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
                RongIM.getInstance().setMessageAttachedUserInfo(true);
                if (callback != null)

                    callback.onSuccess(s);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                RongLogUtils.e(TAG, "------------->连接融云失败 code : " + errorCode);
//                if (callback != null)
////                    callback.onError(errorCode);
            }
        });
    }

    /**
     * 下线
     */
    public void offline() {
        isConnectRonyun = false;
        RongIM.getInstance().disconnect();
        RongIM.getInstance().logout();
        RongIM.getInstance().getRongIMClient().clearConversations(Conversation.ConversationType.PRIVATE);

    }


    /**
     * 设置用户信息
     *
     * @param id
     * @param name
     * @param avater //
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

    /**
     * 输入类型
     * //
     */
    public void setInputProvider(RedPacketProvider.onPluginClickListenter listenter) {
        //扩展功能自定义

        RedPacketProvider redPacketProvider = new RedPacketProvider(RongContext.getInstance());
        redPacketProvider.setOnPluginClickListenter(listenter);
        InputProvider.ExtendProvider[] provider = {
                new ImageInputProvider(RongContext.getInstance()),//图片
                new CameraInputProvider(RongContext.getInstance()),//相机
                new LocationInputProvider(RongContext.getInstance()),//地理位置
                redPacketProvider
//                new VoiceInputProvider(RongContext.getInstance())// 语音通话
//                new ContactsProvider(RongContext.getInstance())//自定义通讯录
        };

        RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, provider);
    }


    public void sendRedPacketMessage(String uid, String content) {
        /**
         * 发送消息。
         *
         * @param type        会话类型。
         * @param targetId    目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
         * @param content     消息内容。
         * @param pushContent push 时提示内容，为空时提示文本内容。
         * @param callback    发送消息的回调。
         * @return
         */
        RongIM.getInstance().getRongIMClient().sendMessage(Conversation.ConversationType.PRIVATE, uid, RedPacketMessage.obtain(content), "", null, null);
    }

    public void setConnectionStatusListener(final ConnectCallback callback) {
        if (RongIM.getInstance() != null && RongIM.getInstance().getRongIMClient() != null) {
            /**
             * 设置连接状态变化的监听器.
             */
            RongIM.getInstance().getRongIMClient().setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
                @Override
                public void onChanged(ConnectionStatus connectionStatus) {
                    switch (connectionStatus) {

                        case CONNECTED://连接成功。
                            RongLogUtils.d(TAG, "连接成功");
//                            callback.onSuccess(ConnectionStatus.CONNECTED.getValue() + "");
                            break;
                        case DISCONNECTED://断开连接。
                            RongLogUtils.d(TAG, "断开连接");
//                            callback.onSuccess(ConnectionStatus.DISCONNECTED.getValue() + "");
                            break;
                        case CONNECTING://连接中。
                            RongLogUtils.d(TAG, "连接中");
//                            callback.onSuccess(ConnectionStatus.CONNECTING.getValue() + "");
                            break;
                        case NETWORK_UNAVAILABLE://网络不可用。
                            RongLogUtils.d(TAG, "网络不可用");
//                            callback.onSuccess(ConnectionStatus.NETWORK_UNAVAILABLE.getValue() + "");
                            break;
                        case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                            if (callback != null)
                                callback.onSuccess(ConnectionStatus.KICKED_OFFLINE_BY_OTHER_CLIENT.getValue() + "");
                            RongLogUtils.d(TAG, "用户账户在其他设备登录，本机会被踢掉线");
                            break;
                    }
                }
            });
        }

    }
}
