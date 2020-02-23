package com.nvmicrosoftwares.sqlitesample;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class ShowData extends AppCompatActivity {

    SQLiteDatabase db;
    GridView gridView;
    ArrayAdapter<String> data;
    ArrayList<String> list;

    private final String DB_NAME = "studentDB";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        initAll();

    }

    private void initAll(){
        db = openOrCreateDatabase(DB_NAME , MODE_PRIVATE, null);
        gridView = findViewById(R.id.grid);
        list = new ArrayList<>();

        Cursor c = db.rawQuery("select * from students" , null);
        int count = c.getColumnCount();

        while(c.moveToNext()){
            list.add(c.getString(0));
            list.add(c.getString(1));
            list.add(c.getString(2));
        }

        data = new ArrayAdapter<>(getApplicationContext() , android.R.layout.simple_gallery_item , list);
        gridView.setAdapter(data);

    }
}
