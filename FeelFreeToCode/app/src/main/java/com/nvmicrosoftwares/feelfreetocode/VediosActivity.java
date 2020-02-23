package com.nvmicrosoftwares.feelfreetocode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nvmicrosoftwares.feelfreetocode.adapters.VedioAdapter;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import bg.devlabs.fullscreenvideoview.FullscreenVideoView;

public class VediosActivity extends AppCompatActivity {

    RecyclerView vedioList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedios);

        String response = getIntent().getStringExtra("response");
        intiAll();


        Gson gson = new Gson();
        Vedio[] vedios = gson.fromJson(response , Vedio[].class);
        Log.e("infoo" , response);

        VedioAdapter adapter = new VedioAdapter(getApplicationContext() , vedios );

        vedioList.setAdapter(adapter);
    }



    private void intiAll(){
        vedioList = findViewById(R.id.vedioList);
        //videoView.videoUrl("http://192.168.43.73:8080/FeelFreeToCode/uploads/java_Script/1/BroadcastReceiver.mp4");
//        videoView.videoUrl("http://192.168.43.73:8080/FeelFreeToCode/file.BroadcastReceiver.mp4");
//        mediaController = findViewById(R.id.vedioMedia);
//        videoView.setMediaController(mediaController);
        vedioList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
