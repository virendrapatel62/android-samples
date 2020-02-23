package com.nvmicrosoftwares.scoreapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button t1 , t2 ;
    TextView tt1 , tt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        tt1 = findViewById(R.id.tt1);
        tt2 = findViewById(R.id.tt2);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tt1.setText((Integer.parseInt(tt1.getText().toString())+1)+"");
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tt2.setText((Integer.parseInt(tt2.getText().toString())+1)+"");
            }
        });

        findViewById(R.id.result).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(tt2.getText().toString()) >
                        Integer.parseInt(tt1.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Team 2 Won", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainActivity.this, "Team 1 Won", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
