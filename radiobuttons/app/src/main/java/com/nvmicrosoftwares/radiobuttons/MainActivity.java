package com.nvmicrosoftwares.radiobuttons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid = findViewById(R.id.grid);
        ArrayList<String> data = new ArrayList<>();
        data.add("Virendra");
        data.add("Hasrh");
        data.add("Hitanshu");
        data.add("Hitanshu");
        data.add("MOhan");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this ,
                android.R.layout.simple_gallery_item , data);

        grid.setAdapter(adapter);
    }
}
