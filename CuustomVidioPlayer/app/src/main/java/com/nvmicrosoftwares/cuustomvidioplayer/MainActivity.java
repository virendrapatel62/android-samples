package com.nvmicrosoftwares.cuustomvidioplayer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import bg.devlabs.fullscreenvideoview.FullscreenVideoView;

public class MainActivity extends AppCompatActivity {

    FullscreenVideoView v ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v = findViewById(R.id.v);
        v.videoUrl("http://techslides.com/demos/sample-videos/small.mp4").playDrawable(R.drawable.thum);
    }
}
