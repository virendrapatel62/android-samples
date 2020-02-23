package com.feelfreetocode.findbestinstitute.activities.student;

import android.content.Intent;
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

import com.feelfreetocode.findbestinstitute.R;
import com.feelfreetocode.findbestinstitute.models.LoginInfo;
import com.feelfreetocode.findbestinstitute.models.Student;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

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
                pb.setVisibility(View.VISIBLE);
                AsyncTask<Integer , Integer , Boolean> asyncTask = new AsyncTask<Integer, Integer, Boolean>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected void onCancelled() {
                        super.onCancelled();
                        pb.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    protected void onPostExecute(Boolean o) {

                        super.onPostExecute(o);
                        pb.setVisibility(View.INVISIBLE);
                        if(o){
                            openHomePage();
                        }else{
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(StudentLogin.this, "Invalid Email Or Password", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }

                    @Override

                    protected Boolean doInBackground(Integer... integers) {
                        s = new Student(em , pass);
                        s = s.login();
                        if(s!=null){
                            Log.e("student login" , s.toString());
                            return true;

                        }else{
                            return false;
                        }
                    }
                };

                asyncTask.execute();

            }
        });
    }

    private void intiAll(){
        email = findViewById(R.id.email);
        pb= findViewById(R.id.progressBar);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.createnew);

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
