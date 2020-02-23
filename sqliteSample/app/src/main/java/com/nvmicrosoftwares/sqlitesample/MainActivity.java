package com.nvmicrosoftwares.sqlitesample;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText name  , addres , branch;
    Button save ,show;
    SQLiteDatabase db;

    private final String DB_NAME = "studentDB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAll();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString();
                String b = branch.getText().toString();
                String a = addres.getText().toString();
                db.execSQL("insert into students values('"+n+"' , '"+a+"' , '"+b+"')");
                name.setText("");
                addres.setText("");
                branch.setText("");
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , ShowData.class);
                startActivity(i);
            }
        });
    }

    private void initAll(){
        name = findViewById(R.id.name);
        addres = findViewById(R.id.address);
        branch = findViewById(R.id.branch);
        save = findViewById(R.id.button);
        show = findViewById(R.id.show);
        db = openOrCreateDatabase(DB_NAME , MODE_PRIVATE , null);
        db.execSQL("create table if not exists students (name varchar , address varchar , branch varchar)");
    }
}
