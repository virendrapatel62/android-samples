package com.feelfreetocode.volley_prac1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    ArrayList<ArrayList> data ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = new ArrayList<>();
        final ListView listView = findViewById(R.id.list);
        final Adapter adapter = new Adapter();
        listView.setAdapter(adapter);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://bldvtest.herokuapp.com/api/user";
        StringRequest stringRequest  = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response" , response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0 ; i < jsonArray.length() ; i++){
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                ArrayList obj = new ArrayList();

                                obj.add(jsonObject.get("name"));

                                data.add(obj);

                            }
                            adapter.notifyDataSetChanged();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }
                ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error" , error.getLocalizedMessage());
                    }
                }
        );

        requestQueue.add(stringRequest);



    }
    class Adapter extends BaseAdapter{
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View  view = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_layout , null);
            ListView l = view.findViewById(R.id.innerlist);
            l.setAdapter(new ArrayAdapter<String>(MainActivity.this , android.R.layout.simple_list_item_1 , data.get(position)));
            return  view;
        }
    }

}
