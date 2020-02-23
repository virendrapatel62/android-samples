package com.nvmicrosoftwares.openurl;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    AsyncTask<String , Integer , String> task ;
    GridView gridView ;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.grid);

    }

    class MyTask extends AsyncTask<String , Integer , String>{
        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected String doInBackground(String... urls) {
            String data = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedInputStream bi = new BufferedInputStream(connection.getInputStream());
                int i = -1;

                byte[] b = new byte[1024];
                while((i = bi.read(b))!=-1){
                    data = data + new String(b);
                }
            }catch(Exception ex){

            }
            return data;
        }
    }





    public  void go(View view){

        try {
            String s  = new MyTask().execute("http://192.168.43.73:8080/attendance/get_all_teachers.do").get();
            Gson gson = new Gson();

            JSONArray o = new JSONArray(s);
            ArrayList<String> teachers = new ArrayList<>();

            for(int i = 0 ; i <o.length() ; i++){
                Teacher t = gson.fromJson(o.get(i).toString() , Teacher.class);
                teachers.add(t.getName());
                teachers.add(t.getContact());
                teachers.add(t.getEmail());
                teachers.add(t.getAddress());
                teachers.add(t.getDesignation().toString());
                Toast.makeText(getApplicationContext() , t.toString(), Toast.LENGTH_LONG).show();
            }

            adapter = new ArrayAdapter<>(getApplicationContext() , android.R.layout.simple_gallery_item , teachers);
            gridView.setAdapter(adapter);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
