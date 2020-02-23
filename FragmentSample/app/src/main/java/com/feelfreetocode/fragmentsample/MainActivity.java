package com.feelfreetocode.fragmentsample;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                if(i == 0 ){
                    fragmentTransaction.replace(R.id.fragment , new GreenFragment());
                    i++;
                }else if(i == 1 ){
                    fragmentTransaction.replace(R.id.fragment , new BlueFragment());
                    i++;
                }else if(i==2){
                        fragmentTransaction.replace(R.id.fragment , new RedFragment());
                        i = 0;
                }
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}
