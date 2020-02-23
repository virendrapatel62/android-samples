package com.nvmicrosoftwares.feelfreetocode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.nvmicrosoftwares.feelfreetocode.adapters.PlayListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView playlists;
    private PlayListAdapter adapter;
    PlayList[] playListData;
    private final String PLAYLIST_API = "http://192.168.43.73:8080/FeelFreeToCode/getplaylists.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playlists = findViewById(R.id.playlists);
        playlists.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        collectAllPlayLists();

    }

    private  void collectAllPlayLists(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, PLAYLIST_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson() ;
                 playListData = gson.fromJson(response ,PlayList[].class);
                adapter = new PlayListAdapter(getApplicationContext() ,playListData);
                playlists.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
