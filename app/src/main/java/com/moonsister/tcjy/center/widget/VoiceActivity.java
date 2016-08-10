package com.moonsister.tcjy.center.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.moonsister.tcjy.R;
import com.moonsister.tcjy.widget.speak.PressToSpeakListenr;
import com.moonsister.tcjy.widget.speak.VoicePlay;

/**
 * Created by jb on 2016/8/10.
 */
public class VoiceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_activity);
        View iv_voice = findViewById(R.id.iv_voice);
        TextView tv_time = (TextView) findViewById(R.id.tv_time);
        iv_voice.setOnTouchListener(new PressToSpeakListenr(this, null) {
            @Override
            public void sendListener(String filePath, String fileName, String length, boolean isResend) {
                setData(filePath, fileName, length, isResend);
            }

            @Override
            public void start() {
                tv_time.setText("开始了");
            }
        });
    }

    private void setData(String filePath, String fileName, String length, boolean isResend) {
        Intent intent = new Intent();
        intent.putExtra("path", filePath);
        intent.putExtra("length", length);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
