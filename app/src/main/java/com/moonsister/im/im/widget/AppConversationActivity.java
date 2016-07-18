package com.moonsister.im.im.widget;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.moonsister.im.R;
import com.moonsister.im.base.BaseActivity;
import com.moonsister.im.event.Events;
import com.moonsister.im.event.RxBus;
import com.moonsister.im.utils.StringUtis;
import com.moonsister.im.utils.UIUtils;
import com.trello.rxlifecycle.ActivityEvent;

import io.rong.imkit.RongyunConfig;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by jb on 2016/6/18.
 */
public class AppConversationActivity extends BaseActivity {

    /**
     * 目标 Id
     */
    private String mTargetId;

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType = Conversation.ConversationType.PRIVATE;


    @Override
    protected View setRootContentView() {
        return UIUtils.inflateLayout(R.layout.appconversation);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        getIntentDate(intent);

    }

    @Override
    protected String initTitleName() {
        String name = getIntent().getData().getQueryParameter("title");
        if (StringUtis.isEmpty(name)) {
            name = RongyunConfig.getInstance().getUserName(mTargetId);
        }
        return name;
    }

    /**
     * 展示如何从 Intent 中得到 融云会话页面传递的 Uri
     */
    private void getIntentDate(Intent intent) {
        mTargetId = intent.getData().getQueryParameter("targetId");
        enterFragment(mConversationType, mTargetId);
    }

    /**
     * 加载会话页面 ConversationFragment
     *
     * @param mConversationType
     * @param mTargetId
     */
    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {

        ConversationFragment fragment = new ConversationFragment();

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragment.setUri(uri);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //xxx 为你要加载的 id
        transaction.add(R.id.conversation, fragment);
        transaction.commit();
        RxBus.with(this)
                .setEndEvent(ActivityEvent.DESTROY)
                .setEvent(Events.EventEnum.CHAT_SEND_REDPACKET_SUCCESS)
                .onNext(events -> {
                    RongyunConfig.getInstance().sendRedPacketMessage(mTargetId, (String) events.message);
                })
                .create();
    }
}
