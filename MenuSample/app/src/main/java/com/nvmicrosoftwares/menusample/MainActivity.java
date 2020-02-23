package com.nvmicrosoftwares.menusample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.item1){
            Toast.makeText(this, "You clicked On Item 1", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.item2){
            Toast.makeText(this, "You clicked On Item 2", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.item3){
            Toast.makeText(this, "You clicked On Item 3", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.item4){
            Toast.makeText(this, "You clicked On Item 4", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
