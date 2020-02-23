package com.feelfreetocode.listviewwithcards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView grid;
    MyAdapter myAdapter ;
    ArrayList<String> data = new ArrayList<>();
    ArrayList<Integer> colors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid = findViewById(R.id.grid);
        myAdapter = new MyAdapter();

        data.add("Aman");
        data.add("Harsh");
        data.add("Hitanshu");
        data.add("Sumit");
        data.add("Ankit");
        data.add("Umang");
        data.add("Arjit");
        data.add("Prakhar");

        colors.add(R.color.color1);
        colors.add(R.color.color2);
        colors.add(R.color.color3);
        colors.add(R.color.color4);
        colors.add(R.color.color5);
        colors.add(R.color.color6);
        colors.add(R.color.color3);
        colors.add(R.color.color4);

        grid.setAdapter(myAdapter);

    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return data.size();
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
            View v  = LayoutInflater.from(getApplicationContext()).inflate(R.layout.itemlayout , null);
            TextView title = v.findViewById(R.id.title);

            title.setBackgroundColor(getResources().getColor(colors.get(position)));
            TextView name = v.findViewById(R.id.name);
            name.setText(data.get(position));
            title.setText(data.get(position).charAt(0)+"");

            return v;
        }
    }
}
