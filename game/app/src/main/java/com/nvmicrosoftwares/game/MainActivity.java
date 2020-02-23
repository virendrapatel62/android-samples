package com.nvmicrosoftwares.game;

import android.media.Image;
import android.nfc.Tag;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    HashMap<ImageView , Integer> imageViews;
    int imageID = 0;
    int tick , cross;
    int temp[];
    TextToSpeech tts ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViews = new HashMap<>();

        imageViews.put((ImageView) findViewById(R.id.imageView),0);
        imageViews.put((ImageView) findViewById(R.id.imageView2),1);
        imageViews.put((ImageView) findViewById(R.id.imageView3),2);
        imageViews.put((ImageView) findViewById(R.id.imageView4),3);
        imageViews.put((ImageView) findViewById(R.id.imageView5),4);
        imageViews.put((ImageView) findViewById(R.id.imageView6),5);
        imageViews.put((ImageView) findViewById(R.id.imageView7),6);
        imageViews.put((ImageView) findViewById(R.id.imageView8),7);
        imageViews.put((ImageView) findViewById(R.id.imageView9),8);


        temp = new int[]{0,1,2,3,4,5,6,7,8};
        tick = R.drawable.tick;
        cross = R.drawable.cross;
        imageID = tick;
        tts = new TextToSpeech(this , this);

        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart();
                imageID = tick;
            }
        });


    }
    public void loadImage(View view){
        ImageView img = (ImageView)view;
        if(temp[imageViews.get(img)] == tick ||  temp[imageViews.get(img)] == cross){
            return ;
        }
        img.setImageResource(imageID);
        temp[imageViews.get(img)] = imageID;
        if(cheak()){
            if(imageID==tick){
                Toast.makeText(getApplicationContext() , "Player 1 is winner" , Toast.LENGTH_LONG).show();
                tts.speak("Congratulations player 1" , TextToSpeech.QUEUE_FLUSH , null);
            }else{
                tts.speak("Congratulations player 2" , TextToSpeech.QUEUE_FLUSH , null);
                Toast.makeText(getApplicationContext() , "Player 2 is winner" , Toast.LENGTH_LONG).show();
            }
            disableAll();
            return;
        }
        if(imageID == tick){
            imageID = cross;
            Toast.makeText(getApplicationContext() , "Now 2nd Player tern" , Toast.LENGTH_SHORT).show();

        }else{
            imageID = tick;

            Toast.makeText(getApplicationContext() , "Now 1nd Player tern" , Toast.LENGTH_SHORT).show();
        }

    }

    private boolean cheak(){
        boolean flag = false;

        if(temp[0] == temp[1] && temp[1] == temp[2]){
            return true;
        }else if(temp[3] == temp[4] && temp[5] == temp[3]){
            return true;
        }
        else if(temp[6] == temp[7] && temp[8] == temp[6]){
            return true;
        }
        else if(temp[0] == temp[3] && temp[6] == temp[0]){
            return true;
        }
        else if(temp[1] == temp[4] && temp[7] == temp[1]){
            return true;
        }else if(temp[2] == temp[5] && temp[8] == temp[2]){
            return true;
        }else if(temp[0] == temp[4] && temp[8] == temp[0]){
            return true;
        }else if(temp[2] == temp[4] && temp[6] == temp[2]){
            return true;
        }
        return flag;
    }

    public void disableAll(){
        Set<ImageView> set = imageViews.keySet();
        Iterator<ImageView> it = set.iterator();
        while(it.hasNext()){
            it.next().setEnabled(false);
        }
    }
    public void restart(){
        Set<ImageView> set = imageViews.keySet();
        Iterator<ImageView> it = set.iterator();
        int n =0;
        while(it.hasNext()){
            ImageView i = it.next();
            i.setEnabled(true);
            temp[imageViews.get(i)] = n++;
            i.setImageResource(R.drawable.common);
        }
    }

    @Override
    public void onInit(int status) {

    }
}
