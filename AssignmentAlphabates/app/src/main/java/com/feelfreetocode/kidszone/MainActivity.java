package com.feelfreetocode.kidszone;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView listView ;
    MediaPlayer mediaPlayer;
    ButtonAdapter buttonAdapter ;
    int[] images  = new int[]{R.mipmap.abc , R.mipmap.numbers, R.mipmap.music};
    int[] colors  = new int[]{R.color.color1 ,R.color.color2 , R.color.color3};
    String[] titles = new String[]{"Alphabates" , "Number", "Rhymes"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(getApplicationContext() , R.raw.bum);

        listView = findViewById(R.id.listView);
        buttonAdapter = new ButtonAdapter();
        listView.setAdapter(buttonAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position ==0){
                    startActivity(new Intent(getApplicationContext() , AlphabatesActivity.class));
                }
                if(position ==1){
                    startActivity(new Intent(getApplicationContext() , Numbers.class));
                }
                if(position ==2){
                    startActivity(new Intent(getApplicationContext() , Rhythems.class));
                }
            }
        });

        mediaPlayer.start();



    }

    @Override
    protected void onPause() {
        mediaPlayer.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mediaPlayer.start();
        super.onResume();
    }

    class ButtonAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return images.length;
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

            imageView.setImageResource(images[position]);
            imageView.setBackgroundColor(getResources().getColor(colors[position]));
            title.setText(titles[position]);
            return view;
        }
    }
}
