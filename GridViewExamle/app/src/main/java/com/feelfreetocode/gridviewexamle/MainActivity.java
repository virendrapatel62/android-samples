package com.feelfreetocode.gridviewexamle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView ;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.grid);

        ArrayList<String> list = new ArrayList<>();

        list.add("Virendra");
        list.add("IT");
        list.add("HItanshu");
        list.add("IT");
        list.add("Harch");
        list.add("CSE");
        list.add("Mohna");
        list.add("ME");



        adapter = new ArrayAdapter<>(getApplicationContext() , android.R.layout.simple_gallery_item , list);

        gridView.setAdapter(adapter);

    }



}
