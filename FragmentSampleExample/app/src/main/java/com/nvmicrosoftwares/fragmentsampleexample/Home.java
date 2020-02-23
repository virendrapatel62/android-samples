package com.nvmicrosoftwares.fragmentsampleexample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;

public class Home extends AppCompatActivity {

    ImageView login , signup ;
    FragmentManager fm;
    FragmentTransaction ft ;
    boolean loginPage = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        login = findViewById(R.id.imageView);
        signup = findViewById(R.id.imageView2);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == login){
                    fm = getSupportFragmentManager();

                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment , new LoginFragment() );
                    ft.commit();
                }
                if(v == signup){
                    fm = getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment, new SignupFragment());
                    ft.commit();
                }
            }
        };

        login.setOnClickListener(onClickListener);
        signup.setOnClickListener(onClickListener);
    }


}
