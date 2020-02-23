package com.feelfreetocode.listviewadds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView ;
    ArrayList<String> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        items = new ArrayList<>();
        items.add("Heelo ");
        items.add("JAva");
        items.add("Python");
        items.add("Android");
        items.add("HTML");
        items.add("Laptop");
        items.add("Heelo ");
        items.add("JAva");
        items.add("Python");
        items.add("Android");
        items.add("HTML");
        items.add("Laptop");
        items.add("Heelo ");
        items.add("JAva");
        items.add("Python");
        items.add("Android");
        items.add("HTML");
        items.add("Laptop");
        items.add("Heelo ");
        items.add("JAva");
        items.add("Python");
        items.add("Android");
        items.add("HTML");
        items.add("Laptop");
        listView = findViewById(R.id.list);
        listView.setAdapter(new ListAdapter());
    }

    class ListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return items.size();
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

            if((position + 1) % 3  == 0 ){
                View view2 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.banneradlayout , null);
                AdView mAdView  = view2.findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
                return view2;
            }else {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_layout, null);
                TextView textView = view.findViewById(R.id.textView);
                textView.setText(items.get(position));
                return view;
            }


        }
    }
}
