package com.feelfreetocode.kidszone;

import android.media.MediaPlayer;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Rhythems extends AppCompatActivity {

    GridView gridView;
    TextToSpeech tts;
    ArrayList<Integer> images  = new ArrayList();
    int[] colors  = new int[]{R.color.color1 ,R.color.color2 , R.color.color3 , R.color.color4 , R.color.color1 ,R.color.color2 , R.color.color3 , R.color.color4 };
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> urls = new ArrayList();
    MediaPlayer mediaPlayer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhythems);
        gridView = findViewById(R.id.list);

        getData();

        mediaPlayer = MediaPlayer.create(getApplicationContext() , Uri.parse(urls.get(0)));

        gridView.setAdapter(new ButtonAdapter());


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mediaPlayer.isPlaying())
                     mediaPlayer.stop();
                    mediaPlayer = MediaPlayer.create(getApplicationContext() , Uri.parse(urls.get(position)));
                mediaPlayer.start();

            }
        });
    }

    private void getData(){
        titles.add("How Are You"); urls.add("http://www.dreamenglish.com/howareyou.mp3");
        titles.add("Name"); urls.add("http://www.dreamenglish.com/name01.mp3");
        titles.add("Good Morning"); urls.add("http://www.dreamenglish.com/mp3/goodmorningsong.mp3");
        titles.add("Hello Song"); urls.add("http://www.dreamenglish.com/hellosong.mp3");
        titles.add("Big World"); urls.add("http://www.dreamenglish.com/mp3/bigworld.mp3");
        titles.add("Open Shut Then"); urls.add("http://www.dreamenglish.com/mp3/openshutthem.mp3");
        titles.add("Body Parts "); urls.add("http://www.dreamenglish.com/mp3/headshoulderskneestoes.mp3");
        titles.add("ABC Song"); urls.add("http://www.dreamenglish.com/Dream%20English%20Traditional%20ABC01.mp3");

        images.add(R.mipmap.h);
        images.add(R.mipmap.n);
        images.add(R.mipmap.g);
        images.add(R.mipmap.h);
        images.add(R.mipmap.b);
        images.add(R.mipmap.o);
        images.add(R.mipmap.b);
        images.add(R.mipmap.a);
    }

    @Override
    protected void onDestroy() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        super.onDestroy();
    }

    class ButtonAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.home_screen_buttons , null);
            ImageView imageView = view.findViewById(R.id.image);
            TextView title = view.findViewById(R.id.title);

            imageView.setImageResource(images.get(position));
                imageView.setBackgroundColor(getResources().getColor(colors[position]));
            title.setText(titles.get(position));
            return view;
        }
    }
}
