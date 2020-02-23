package com.nvmicrosoftwares.gridsamplewithimages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    GridView gridView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.grid);

        List<Integer> images = new ArrayList<>();
        List<String> names = new ArrayList<>();

        images.add(R.drawable.a);
        images.add(R.drawable.b);
        images.add(R.drawable.c);
        images.add(R.drawable.d);
        images.add(R.drawable.e);

        names.add("Mohan");
        names.add("Sohan");
        names.add("Lakhan");
        names.add("John");
        names.add("Harsh");



        StudentAdapter adapter = new StudentAdapter(this ,images , names );
        gridView.setAdapter(adapter);


    }
}
