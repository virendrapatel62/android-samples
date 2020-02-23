package com.feelfreetocode.listviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView list ;
    ArrayAdapter<String> adapter;
    ArrayList<String> items;
    Button buton;
    EditText item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buton = findViewById(R.id.button);
        item = findViewById(R.id.editText);

        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s  = item.getText().toString();
                adapter.add(s);
                adapter.notifyDataSetChanged();
            }
        });

        items = new ArrayList<>();
        items.add("jabalpur");
        items.add("Mumbai");
        items.add("bhopal");
        items.add("Laptop");

        list = findViewById(R.id.list);
        adapter = new ArrayAdapter<>(getApplicationContext() , R.layout.list_item_layout , R.id.itemTextView , items);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove( items.get(position));
                adapter.notifyDataSetChanged();
                Log.e("Item" , items.get(position));
                Toast.makeText(MainActivity.this, "Clicked on Item", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
