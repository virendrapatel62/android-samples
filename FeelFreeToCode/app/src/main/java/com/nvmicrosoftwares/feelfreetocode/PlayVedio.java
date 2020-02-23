package com.nvmicrosoftwares.feelfreetocode;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

import bg.devlabs.fullscreenvideoview.FullscreenVideoView;

public class PlayVedio extends AppCompatActivity {

    VideoView videoView;
    MediaController mediaController ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_vedio);

        videoView = findViewById(R.id.v);
        Toast.makeText(getApplicationContext() ,getIntent().getStringExtra("url") , Toast.LENGTH_LONG).show();
      String url =     getIntent().getStringExtra("url");
//         videoView.videoUrl(url);
        Log.e("infoooo" , url);
        videoView.setVideoURI(Uri.parse(url.trim().replace(" " , "")));
        videoView.start();
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.start();
//        videoView.videoUrl("http://192.168.43.73:8080/FeelFreeToCode/uploads/java_Script/1/BroadcastReceiver.mp4");
    }
}
