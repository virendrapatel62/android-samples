package com.nvmicrosoftwares.listassign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        ((TextView)findViewById(R.id.textView)).setText(getIntent().getStringExtra("key"));
    }
}
