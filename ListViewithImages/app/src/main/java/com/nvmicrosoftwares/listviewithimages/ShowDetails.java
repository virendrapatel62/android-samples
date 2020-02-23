package com.nvmicrosoftwares.listviewithimages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        Intent i = getIntent();
        ((TextView)findViewById(R.id.textView)).setText(i.getStringExtra("name"));
        ((ImageView)(findViewById(R.id.imageView2))).setImageResource(i.getIntExtra("image" , R.drawable.ic_launcher_background));
    }
}
