package com.example.myapplication.ui;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.ui.activity.BaseActivity;
import com.example.myapplication.ui.widget.CustomVideoView;

public class WelcomeActivity extends BaseActivity {

    private Button welcome_button;
    private CustomVideoView welcome_vedioview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcome_button = findViewById(R.id.welcome_buotton);
        welcome_vedioview = findViewById(R.id.welcome_vedioView);
        welcome_vedioview.setVideoURI(Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.kr36));
        welcome_vedioview.start();
        welcome_vedioview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                welcome_vedioview.start();
            }
        });
        welcome_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (welcome_vedioview.isPlaying()){
                    welcome_vedioview.stopPlayback();
                    welcome_vedioview = null;
                }
                openActivity(MainActivity.class);
                WelcomeActivity.this.finish();
            }
        });
    }
}
