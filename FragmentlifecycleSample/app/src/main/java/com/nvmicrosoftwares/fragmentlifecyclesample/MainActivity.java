package com.nvmicrosoftwares.fragmentlifecyclesample;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    BlankFragment f;
    boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "OnCreate");

        f = new BlankFragment();

        Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                if(flag){
                    fm.beginTransaction().replace(R.id.fragment , f).commit();
                    flag = false;
                }else{
                    fm.beginTransaction().remove(f).commit();
                    finish();
                    flag = true;
                }



            }
        });
    }


    @Override
    protected void onStart() {

        super.onStart();
        Log.d("MainActivity", "OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "OnCreate");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy");
    }
}
