package com.nvmicrosoftwares.listassign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<String> data ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);
        data = new ArrayList<>();

        data.add("India");
        data.add("Pakistan");
        data.add("China");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 , data);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                String s = (String) listView.getItemAtPosition(position);
                arrayAdapter.remove(arrayAdapter.getItem(position));
                Intent it = new Intent(MainActivity.this , ShowData.class
                );
                it.putExtra("key" , s);
               // startActivity(it);
            }
        });

    }
}
