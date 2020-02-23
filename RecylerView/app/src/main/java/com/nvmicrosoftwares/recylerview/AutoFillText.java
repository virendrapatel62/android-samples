package com.nvmicrosoftwares.recylerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AutoFillText extends AppCompatActivity {


    AutoCompleteTextView textView ;
    RequestQueue requestQueue ;

    String URL = "https://apis.mapmyindia.com/advancedmaps/v1/l2dlc7zabjd6wx8ekzjo2ezzvt21i8b3/geo_code?addr=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_fill_text);



        textView = findViewById(R.id.autoCompleteTextView);
        textView.setDropDownBackgroundResource(android.R.color.background_dark);

        final ArrayList<String> list = new ArrayList<>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1  , list);
        textView.setAdapter(adapter);

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                StringRequest stringRequest = new StringRequest( Request.Method.GET, URL+textView.getText().toString() , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        list.clear();
                        Log.e("info" , response);
                        Gson gson = new Gson();
                        try {
                            Location location = gson.fromJson(response, Location.class);
                            List<Result> locations = location.getResults();
                            if (location != null)
                                for (Result l : locations) {
                                    if (l.getDistrict() != null) {
                                        list.add(l.getDistrict());
                                        Log.e("Data" , list.toString());
                                    } else {
                                    }

                                }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext() , android.R.layout.simple_list_item_1  , list);
                            adapter.notifyDataSetChanged();
                            textView.setAdapter(adapter);
                            textView.showDropDown();
                        }catch (Exception ex){

                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });


                Toast.makeText(AutoFillText.this, textView.getText().toString(), Toast.LENGTH_SHORT).show();
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });





    }
}
