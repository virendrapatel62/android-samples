package com.nvmicrosoftwares.validation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((RatingBar)(findViewById(R.id.ratingBar))).getRating()>0){
                        startActivity(new Intent(MainActivity.this , nextPage.class));

                }else{
                    Toast.makeText(getApplicationContext() , "Please Rate First" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
