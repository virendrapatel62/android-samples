package com.nvmicrosoftwares.splashscreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread th=new Thread(){
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                    Intent it=new Intent(MainActivity.this,Registration.class);
                    startActivity(it);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }

            }
        };
        th.start();
    }
}
