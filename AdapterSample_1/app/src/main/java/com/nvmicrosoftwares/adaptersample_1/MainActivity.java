package com.nvmicrosoftwares.adaptersample_1;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView list ;
    Button save ;
    EditText text ;
    ArrayAdapter<String> data;
    ArrayList arrayList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.editText);
        save = findViewById(R.id.button);
        list = findViewById(R.id.list);
        arrayList = new ArrayList();
        data = new ArrayAdapter<>(this ,android.R.layout.simple_list_item_1 , arrayList);

        list.setAdapter(data);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.add(text.getText().toString());
            }
        });

    }
}
