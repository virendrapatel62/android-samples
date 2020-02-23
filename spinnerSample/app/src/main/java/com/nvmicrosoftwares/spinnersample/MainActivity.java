package com.nvmicrosoftwares.spinnersample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Spinner spinner ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        spinner = findViewById(R.id.spinner);

        ArrayList<Integer> pics = new ArrayList();
        ArrayList<String> names = new ArrayList();

        pics.add(R.raw.a);
        pics.add(R.raw.b);
        pics.add(R.raw.c);
        pics.add(R.raw.d);

        names.add("First Student");
        names.add("Second Student");
        names.add("Third Student");
        names.add("Fourth Student");

        StudentAdapter adapter =  new StudentAdapter(this , pics , names );
        spinner.setAdapter(adapter);

       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView parent, View view, int position, long id) {
               Toast.makeText(MainActivity.this, "Seletected" + position, Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
    }
}
