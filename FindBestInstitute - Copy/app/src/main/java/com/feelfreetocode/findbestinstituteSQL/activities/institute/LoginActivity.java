package com.feelfreetocode.findbestinstituteSQL.activities.institute;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.feelfreetocode.findbestinstituteSQL.R;
import com.feelfreetocode.findbestinstituteSQL.constants.API;
import com.feelfreetocode.findbestinstituteSQL.models.Institute;
import com.feelfreetocode.findbestinstituteSQL.models.LoginInfo;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class LoginActivity extends AppCompatActivity {

    private EditText email , password ;
    private Button login;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initAll();
        setClickListener();
    }

    private void initAll(){
        login = findViewById(R.id.loginbutton);
        email = findViewById(R.id.loginemail);


        progressBar= findViewById(R.id.intituteloginprograssbar);
        password  = findViewById(R.id.loginpassword);

        email.setText("mohan@gmail.com");
        password.setText("123456");
    }

    private void setClickListener(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getting data

                final String em = email.getText().toString().trim();
                final String pass = password.getText().toString().trim();

                // validation

                if (em.isEmpty()) {
                    email.setError("Enter Email");
                } else if (pass.isEmpty()) {
                    password.setError("Enter Password");
                }

                final Uri.Builder builder = Uri.parse(API.INSTITUTE_LOGIN).buildUpon();
                builder.appendQueryParameter("email" , em);
                builder.appendQueryParameter("password" , pass);


                        StringRequest loginRequest = new StringRequest(Request.Method.GET, builder.toString(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equalsIgnoreCase("false")){
                                    Toast.makeText(LoginActivity.this, "Email or Password Incorrect", Toast.LENGTH_SHORT).show();
                                }else{
                                    String json = response;
                                    Gson gson = new Gson();
                                    Institute institute = (Institute)gson.fromJson(response , Institute.class);
                                    Log.e("-----------" , institute.toString());
                                    Intent it = new Intent(LoginActivity.this, InstituteHome.class);
                                    it.putExtra("institute" , institute);
                                    startActivity(it);

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this, "Check Internet Conenction", Toast.LENGTH_SHORT).show();
                            }
                        });

                        Volley.newRequestQueue(getApplicationContext()).add(loginRequest);



            }
        });

                ((Button) findViewById(R.id.insSignup)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent it = new Intent(LoginActivity.this, Registration.class);
                        startActivity(it);
                    }
                });
            }
        }



