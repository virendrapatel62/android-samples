package com.feelfreetocode.findbestinstituteSQL.activities.student;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.feelfreetocode.findbestinstituteSQL.R;
import com.feelfreetocode.findbestinstituteSQL.constants.API;
import com.feelfreetocode.findbestinstituteSQL.models.LoginInfo;
import com.feelfreetocode.findbestinstituteSQL.models.Student;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

public class StudentLogin extends AppCompatActivity {

    EditText email , password;
    ProgressBar pb ;
    Button login , signup;
    Student s;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        handler = new Handler(getMainLooper());
        intiAll();
        setClickListener();
    }

    private void setClickListener(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String em = email.getText().toString().trim();
                final String pass= password.getText().toString().trim();

                if(em.isEmpty()){
                    email.setError("Enter Email");
                    return;
                }
                if(pass.isEmpty()){
                    password.setError("Enter Password");
                    return;
                }

                Uri.Builder builder = Uri.parse(API.STUDENT_LOGIN).buildUpon();
                builder.appendQueryParameter("email" , em);
                builder.appendQueryParameter("password" , pass);
                StringRequest loginStringRequest = new StringRequest(Request.Method.GET, builder.toString(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equalsIgnoreCase("false")){
                            Toast.makeText(StudentLogin.this, "Email or password wrong", Toast.LENGTH_SHORT).show();
                        }else{
                            String re = response;
                            Student student = (Student)new Gson().fromJson(response , Student.class);
                            Toast.makeText(StudentLogin.this, "Login Success", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(StudentLogin.this , StudentHomeScreen.class);
                            i.putExtra("student" , student);
                            startActivity(i);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(StudentLogin.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                Volley.newRequestQueue(getApplicationContext()).add(loginStringRequest);

            }
        });
    }

    private void intiAll(){
        email = findViewById(R.id.email);
        pb= findViewById(R.id.progressBar);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.createnew);

        email.setText("v@gmail.com");
        password.setText("123456");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentLogin.this , StudentRegistration.class));
            }
        });
    }

    private void openHomePage(){
        Intent i = new Intent(this , StudentHomeScreen.class);
        try{
            FileOutputStream fos = openFileOutput("studentUser" , MODE_PRIVATE );
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(s);
            Log.e("Writting" , s+"");
            fos = openFileOutput("login" , MODE_PRIVATE );
            oos = new ObjectOutputStream(fos);
            oos.writeObject(new LoginInfo(s.getEmail() , s.getPassword() , "student"));
        }catch(Exception ex){
            ex.printStackTrace();
        }
        i.putExtra("student" , s);
        startActivity(i);
    }
}
