package com.feelfreetocode.findbestinstitute.activities.institute;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.feelfreetocode.findbestinstitute.R;
import com.feelfreetocode.findbestinstitute.models.Institute;
import com.feelfreetocode.findbestinstitute.webservicesApi.Location;
import com.feelfreetocode.findbestinstitute.webservicesApi.MapPlaceResult;
import com.google.gson.Gson;

import java.util.List;

public class Registration extends AppCompatActivity {

    private EditText instituteName , address , city , state  , adminName , contact , email , password ;
    private String lat="" , lang="";
    private TextView haveAcount;
    private AutoCompleteTextView location;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initAll();
        setListenersOnLocationEditText();
        setClickListenerOnButton();
    }

    private void initAll(){
        instituteName = findViewById(R.id.instituteName);
        address = findViewById(R.id.addr);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        adminName = findViewById(R.id.adminName);
        contact = findViewById(R.id.contactNumber);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        location = findViewById(R.id.location);
        haveAcount = findViewById(R.id.haveaccount);

    }

    private void clearAllFields(){
        instituteName.setText("");
        address.setText("");
        city.setText("");
        state.setText("");
        adminName.setText("");
        contact.setText("");
        email.setText("");
        password.setText("");
        location.setText("");
        lat = "";
        lang = "";
    }


    private void setClickListenerOnButton(){

        haveAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginPage();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                //collecting all values
                String em =  email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String insName = instituteName.getText().toString().trim();
                String addr = address.getText().toString().trim();
                String latitute = lat.toString().trim();
                String longitude = lang.toString().trim();
                String stt = state.getText().toString().trim();
                String ct = city.getText().toString().trim();
                String adName = adminName.getText().toString().trim();
                String con = contact.getText().toString().trim();


                if(em.isEmpty() || pass.isEmpty()
                        || insName.isEmpty() || addr.isEmpty()
                        || stt.isEmpty() || ct.isEmpty()
                        || adName.isEmpty() || con.isEmpty()){

                    Toast.makeText(Registration.this, "All Fields Are Required", Toast.LENGTH_LONG).show();
                    return ;
                }

                if(latitute.isEmpty()|| longitude.isEmpty()){
                    Toast.makeText(Registration.this, "Location is invalid , Please Choose from list ", Toast.LENGTH_SHORT).show();
                    return ;
                }

                Institute i = new Institute(insName ,addr,ct,stt ,latitute,longitude,adName,con,em ,pass);
                boolean y = i.registration();
                if(y){
                    Toast.makeText(Registration.this, "Registration Successful....", Toast.LENGTH_LONG).show();
                }
                Log.e("infooo " , y+"");
            }
        });

    }

    private void setListenersOnLocationEditText(){

        // when you select any location from dropdown
        location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Location l = (Location) location.getAdapter().getItem(position);
                lat = l.getLat();
                lang = l.getLng();
                state.setText(l.getState());
                city.setText(l.getDistrict());


                Log.i("infoo  lat " , lat);
                Log.i("infooo  lang " , lang);
                Log.i("info item Seleted " , location.getAdapter().getItem(position).toString()+" OnItemCLiked");
//                Toast.makeText(Registration.this, location.getAdapter().getItem(position).toString()+" OnItemCLiked", Toast.LENGTH_SHORT).show();
            }
        });


        // collecting location acording to text you typed
        location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String url = "https://apis.mapmyindia.com/advancedmaps/v1/l2dlc7zabjd6wx8ekzjo2ezzvt21i8b3/geo_code?addr="+location.getText().toString().trim();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        MapPlaceResult mapPlaceResult =  gson.fromJson(response , MapPlaceResult.class);
                        List<Location> locations = mapPlaceResult.getResults();
                        if(locations!=null){
                            location.setAdapter(new ArrayAdapter<Location>(
                                                                        getApplicationContext() ,
                                                                        R.layout.autocomplete_edittext_list ,
                                                                        R.id.text,
                                                                        locations.toArray(new Location[0])));
                            location.showDropDown();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Registration.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });

                Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void startLoginPage(){
        Intent i = new Intent(getApplicationContext() , LoginActivity.class);
        startActivity(i);
    }
}
