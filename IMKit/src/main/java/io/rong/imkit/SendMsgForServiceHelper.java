package io.rong.imkit;


import android.text.TextUtils;

import com.google.gson.Gson;

import io.rong.imkit.userInfoCache.RongUserInfoManager;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;
import io.rong.message.RichContentMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jb on 2016/7/29.
 */
public class SendMsgForServiceHelper {
    public enum ChatType {
        TXT_MSG("RC:TxtMsg"), IMG_MSG("RC:ImgMsg"), VC_MSG("RC:VcMsg"), IMG_TEXTMSG("RC:ImgTextMsg"), LBS_MSG("RC:LBSMsg");
        private final String type;

        private ChatType(String type) {
            this.type = type;
        }

        public String getValue() {
            return type;
        }

    }

    private static void sendMsg(ChatType chatType, String touid, MsgContent context) {
        String s = new Gson().toJson(context);
        if (TextUtils.isEmpty(s))
            return;
        Observable<MsgBean> observable = RongServerAPI.getRongAPI().send(chatType.getValue(), touid, s);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<MsgBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        RongLogUtils.d(SendMsgForServiceHelper.class, "Throwable : " + e.getMessage());
                    }

                    @Override
                    public void onNext(MsgBean msgBean) {
                        RongLogUtils.d(SendMsgForServiceHelper.class, "msgBean" + msgBean.toString());
                    }
                });
    }

    public void send(Message message) {
        MessageContent messageContent = message.getContent();
        if (messageContent instanceof TextMessage) {// 文本消息
            TextMessage textMessage = (TextMessage) messageContent;


            RongLogUtils.e(this, "onSent-TextMessage:" + textMessage.getContent());

            MsgContent msgContent = new MsgContent();
            msgContent.setContent(textMessage.getContent());
            msgContent.setUser(gerUser(message.getUId()));
            sendMsg(ChatType.TXT_MSG, message.getTargetId(), msgContent);
            // sendSync(message, "2", textMessage.getContent());
        } else if (messageContent instanceof ImageMessage) {// 图片消息
            ImageMessage imageMessage = (ImageMessage) messageContent;
            RongLogUtils.e(this, "onSent-ImageMessage:" + imageMessage.getLocalUri().getPath());
        } else if (messageContent instanceof VoiceMessage) {// 语音消息
            VoiceMessage voiceMessage = (VoiceMessage) messageContent;
            RongLogUtils.e(this, "onSent-voiceMessage:"
                    + voiceMessage.getUri().toString());

        } else if (messageContent instanceof RichContentMessage) {// 图文消息
            RichContentMessage richContentMessage = (RichContentMessage) messageContent;
            RongLogUtils.e(this,
                    "onSent-RichContentMessage:"
                            + richContentMessage.getContent());
        } else {
            RongLogUtils.e(this, "onSent-其他消息，自己来判断处理");
        }
    }

    private MsgContent.UserBean gerUser(String mTargetId) {
        UserInfo userInfo = RongUserInfoManager.getInstance().getUserInfo(mTargetId);
        if (userInfo == null)
            return null;

        MsgContent.UserBean userBean = new MsgContent.UserBean();
        userBean.setIcon(userInfo.getPortraitUri().getPath());
        userBean.setId(mTargetId);
        userBean.setName(userInfo.getName());
        return userBean;
    }


}



