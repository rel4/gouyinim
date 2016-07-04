package com.gouyin.im.im.widget;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.gouyin.im.R;

import java.util.Locale;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by jb on 2016/6/18.
 */
public class AppConversationActivity extends FragmentActivity {

    /**
     * 目标 Id
     */
    private String mTargetId = "11";

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType = Conversation.ConversationType.PRIVATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appconversation);
        Intent intent = getIntent();

        getIntentDate(intent);
        String name = intent.getStringExtra("name");
        TextView tv_title_name = (TextView) findViewById(R.id.tv_title_name);
        tv_title_name.setText(name);
    }

    /**
     * 展示如何从 Intent 中得到 融云会话页面传递的 Uri
     */
    private void getIntentDate(Intent intent) {
        mTargetId = intent.getStringExtra("id");

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
    }
}
