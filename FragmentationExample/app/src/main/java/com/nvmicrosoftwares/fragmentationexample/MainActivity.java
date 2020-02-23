package com.nvmicrosoftwares.fragmentationexample;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    boolean flag = true;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag) {
                    FragmentManager  fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    FirstFragment ff = new FirstFragment();
                    fragmentTransaction.replace(R.id.fragment, ff);
                    fragmentTransaction.commit();
                    flag = false;
                }else{
                    flag = true;
                    FragmentManager  fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    secondFragment ff = new secondFragment();
                    fragmentTransaction.replace(R.id.fragment , ff);
                    fragmentTransaction.commit();
                }
            }
        });
    }

}
