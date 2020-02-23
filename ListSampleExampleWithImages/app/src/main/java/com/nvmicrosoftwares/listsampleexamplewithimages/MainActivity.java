package com.nvmicrosoftwares.listsampleexamplewithimages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView ;
    private ArrayList<Integer> pics;
    private ArrayList<String> names;
    private LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inflater = LayoutInflater.from(getApplicationContext());

        listView = findViewById(R.id.list);
        pics = new ArrayList<>();
        names = new ArrayList<>();

        pics.add(R.drawable.a);
        pics.add(R.drawable.b);
        pics.add(R.drawable.e);
        pics.add(R.drawable.d);
        pics.add(R.drawable.e);

        names.add("Mohan");
        names.add("Sohan");
        names.add("Rohan");
        names.add("John");
        names.add("Hitesh");

        listView.setAdapter(new StudentAdapter());

    }

    class StudentAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return names.size();
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if(position%2==0) {
                view = inflater.inflate(R.layout.student_data_layout, null);
                TextView textView = view.findViewById(R.id.textView);
                textView.setText(names.get(position));

                ImageView imageView = view.findViewById(R.id.imageView);
                imageView.setImageResource(pics.get(position));
            }else{
                view = inflater.inflate(R.layout.student_layout_second, null);
                TextView textView = view.findViewById(R.id.textView2);
                textView.setText(names.get(position));

                ImageView imageView = view.findViewById(R.id.imageView2);
                imageView.setImageResource(pics.get(position));
            }
            return view;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }
    }
}
