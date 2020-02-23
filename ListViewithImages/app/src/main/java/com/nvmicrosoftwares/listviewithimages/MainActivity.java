package com.nvmicrosoftwares.listviewithimages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView ;
    StudentAdpater adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);
        final List<Integer> pics = new ArrayList<>();
        final List<String> names= new ArrayList<>();
        pics.add(R.raw.a);
        pics.add(R.raw.b);
        pics.add(R.raw.c);
        pics.add(R.raw.d);
        pics.add(R.raw.e);

        names.add("Hitanshu");
        names.add("Virendra");
        names.add("Harsh");
        names.add("Prakhar");
        names.add("Sumit");

        adapter = new StudentAdpater(this , pics , names);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this , ShowDetails.class);
                i.putExtra("image" , pics.get(position));
                i.putExtra("name" , names.get(position));
                startActivity(i);
            }
        });
    }
}
