package com.nvmicrosoftwares.gridviewimagesexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GridView gridView ;
    StudentAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.grid);

        List<Integer> pics = new ArrayList<>();
        List<String> names= new ArrayList<>();
        pics.add(R.drawable.a);
        pics.add(R.drawable.b);
        pics.add(R.drawable.c);
        pics.add(R.drawable.d);
        pics.add(R.drawable.e);

        names.add("Mohan");
        names.add("Sohan");
        names.add("Rohan");
        names.add("Virendra");
        names.add("HItanshu");

        adapter = new StudentAdapter(this , pics  , names);
        gridView.setAdapter(adapter);
    }

    public void add(View view){
        adapter.add(R.drawable.a , "Virendra");
        adapter.notifyDataSetChanged();
    }
}
