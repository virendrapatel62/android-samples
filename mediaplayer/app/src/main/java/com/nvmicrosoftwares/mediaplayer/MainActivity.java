package com.nvmicrosoftwares.mediaplayer;

import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp ;
    SeekBar sk ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp = MediaPlayer.create(this ,R.raw.song );
        sk = findViewById(R.id.seekBar);
        sk.setMax(mp.getDuration());
        sk.setProgress(0);

        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mp.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                sk.setProgress(mp.getCurrentPosition());
            }

        };

        final Timer t = new Timer();
        t.schedule(tt , 0 , 1000);

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                t.cancel();
            }
        });
    }

    public void doWork(View view){
        Button b = (Button)view;
        if(b ==  findViewById(R.id.button)){
            if(!mp.isPlaying())
                mp.start();
            ((ImageView)findViewById(R.id.imageView)).setImageResource(R.drawable.play);
        }else if(b ==  findViewById(R.id.button2)){
            mp.pause();
            ((ImageView)findViewById(R.id.imageView)).setImageResource(R.drawable.pause);
        }else if(b ==   findViewById(R.id.button3)){
            mp.stop();
            ((ImageView)findViewById(R.id.imageView)).setImageResource(R.drawable.pause);
            mp = MediaPlayer.create(this ,R.raw.song );

        }
    }
}
