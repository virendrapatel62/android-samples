package com.nvmicrosoftwares.shoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ItemList extends AppCompatActivity {

    ListView listitems ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        listitems = findViewById(R.id.listItems);

        final ArrayList<String> items = new ArrayList<>();
        items.add("Rice");
        items.add("Pulse");
        items.add("Potato");
        items.add("Cheese");
        items.add("Buiscit");
        items.add("Tomato");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1 , items);

        listitems.setAdapter(adapter);

        listitems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.adapter.add(items.get(position));
                MainActivity.adapter.notifyDataSetChanged();
//                getIntent().putExtra("item" ,items.get(position) );
                finish();
            }
        });

    }
}
