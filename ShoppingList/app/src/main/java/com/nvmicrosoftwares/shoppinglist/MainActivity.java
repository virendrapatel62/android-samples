package com.nvmicrosoftwares.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ArrayList<String> items = new ArrayList<>();
    static ArrayAdapter<String> adapter ;
    ListView list ;
    Button add;
    Intent i ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);
        add = findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext() , ItemList.class);
                startActivity(i);
            }
        });

        adapter = new ArrayAdapter<>(getApplicationContext() , android.R.layout.simple_list_item_1 , items);
        list.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        try {
            Toast.makeText(getApplicationContext() , i.getStringExtra("item")+"", Toast.LENGTH_SHORT).show();
        }catch (Exception ex){

        }
        super.onResume();
    }
}
