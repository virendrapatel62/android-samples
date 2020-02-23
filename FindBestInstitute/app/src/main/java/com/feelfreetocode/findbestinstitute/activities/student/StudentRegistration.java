package com.feelfreetocode.findbestinstitute.activities.student;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.feelfreetocode.findbestinstitute.R;
import com.feelfreetocode.findbestinstitute.models.Student;
import com.feelfreetocode.findbestinstitute.webservicesApi.Location;
import com.feelfreetocode.findbestinstitute.webservicesApi.MapPlaceResult;
import com.google.gson.Gson;

import java.util.List;

public class StudentRegistration extends AppCompatActivity {

   private  EditText name , email , password ,contact;
   private AutoCompleteTextView address;
   String lat="" , lang="", state="" , city="";
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        initAll();

        setClickListener();
    }

    private void setClickListener(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openLoginScreen();
                String nm = name.getText().toString().trim();
                String em = email.getText().toString().trim();
                String pass= password.getText().toString().trim();
                String con = contact.getText().toString().trim();
                String addr = address.getText().toString().trim();
                if(nm.isEmpty()){
                    name.setError("ENter Name");
                    return ;
                }
                if(pass.isEmpty()){
                    password.setError("ENter Name");
                    return ;
                }
                if(em.isEmpty()){
                    email.setError("ENter Name");
                    return ;
                }
                if(con.isEmpty()){
                    contact.setError("ENter Name");
                    return ;
                }if(addr.isEmpty()){
                    address.setError("ENter Name");
                    return ;
                }
                if(lat.isEmpty() || lang.isEmpty()||  state.isEmpty()||city.isEmpty() ){
                    address.setError("Location not Selected , Select Location From Dropdown");
                    return ;
                }

                Student student = new Student( nm , em , pass ,con, addr,state , city , lat , lang);
                boolean flag = student.save();
                if(flag){
                    Toast.makeText(StudentRegistration.this, "Registration Success", Toast.LENGTH_SHORT).show();
                    openLoginScreen();
                }else{
                    Toast.makeText(StudentRegistration.this, "Registration Failed !!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Location l = (Location) address.getAdapter().getItem(position);
                lat = l.getLat();
                lang = l.getLng();
                state = l.getState();

                city = l.getDistrict();

                ((EditText)findViewById(R.id.state)).setText(state);
                ((EditText)findViewById(R.id.dis)).setText(city);

                Log.i("infoo  lat " , lat);
                Log.i("infooo  lang " , lang);
                Log.i("info item Seleted " , address.getAdapter().getItem(position).toString()+" OnItemCLiked");
//                Toast.makeText(Registration.this, location.getAdapter().getItem(position).toString()+" OnItemCLiked", Toast.LENGTH_SHORT).show();
            }
        });


        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String url = "https://apis.mapmyindia.com/advancedmaps/v1/l2dlc7zabjd6wx8ekzjo2ezzvt21i8b3/geo_code?addr="+address.getText().toString().trim();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        MapPlaceResult mapPlaceResult =  gson.fromJson(response , MapPlaceResult.class);
                        List<Location> locations = mapPlaceResult.getResults();
                        if(locations!=null){
                            address.setAdapter(new ArrayAdapter<Location>(
                                    getApplicationContext() ,
                                    R.layout.autocomplete_edittext_list ,
                                    R.id.text,
                                    locations.toArray(new Location[0])));
                            address.showDropDown();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StudentRegistration.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });

                Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void openLoginScreen(){
        Intent i = new Intent(this , StudentLogin.class);
        startActivity(i);
    }

    private void initAll(){
        name = findViewById(R.id.studentName);
        email = findViewById(R.id.studentEmail);
        password = findViewById(R.id.studentPassword);
        contact = findViewById(R.id.contact);
        address = findViewById(R.id.location);
        save = findViewById(R.id.signup);
    }

    private void clearAll(){
        clear(name);
        clear(email);
        clear(password);
        clear(contact);
        clear(address);
        clear((EditText) findViewById(R.id.state));
        clear((EditText) findViewById(R.id.city));
    }

    private void clear(EditText text){
        text.setText("");
    }
}
