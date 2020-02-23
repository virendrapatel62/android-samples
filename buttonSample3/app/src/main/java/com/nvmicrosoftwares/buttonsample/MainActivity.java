package com.nvmicrosoftwares.buttonsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void click( View view){
        Toast.makeText(this, "CLick Using Onlick Attribute", Toast.LENGTH_SHORT).show();
    }



}
