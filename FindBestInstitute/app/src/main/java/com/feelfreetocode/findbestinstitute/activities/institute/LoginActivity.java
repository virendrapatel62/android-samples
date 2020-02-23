package com.feelfreetocode.findbestinstitute.activities.institute;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.feelfreetocode.findbestinstitute.R;
import com.feelfreetocode.findbestinstitute.models.Institute;
import com.feelfreetocode.findbestinstitute.models.LoginInfo;

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
    }

    private void setClickListener(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getting data

                final String em = email.getText().toString().trim();
                final String pass = password.getText().toString().trim();

                // validation

                if(em.isEmpty()){
                    email.setError("Enter Email");
                }else if(pass.isEmpty()){
                    password.setError("Enter Password");
                }
                progressBar.setVisibility(View.VISIBLE);

                AsyncTask<Integer , Integer , Boolean> logintast =new AsyncTask<Integer, Integer, Boolean>() {
                    Institute i;
                    @Override
                    protected Boolean doInBackground(Integer... integers) {
                        i = new Institute(em , pass);
                        i = i.login();
                        if(i == null){
                            return  false;
                        }
                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        progressBar.setVisibility(View.INVISIBLE);
                        if(aBoolean){
                            Intent intent =new Intent(getApplicationContext() , InstituteHome.class);
                            intent.putExtra("institute" , i);
                            try{
                                FileOutputStream fos = openFileOutput("instituteUser" , MODE_PRIVATE );
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(i);
                                Log.e("Writting" , i+"");
                                fos = openFileOutput("login" , MODE_PRIVATE );
                                oos = new ObjectOutputStream(fos);
                                oos.writeObject(new LoginInfo(i.getEmail() , i.getPassword() , "institute"));
                            }catch(Exception ex){
                                ex.printStackTrace();
                            }

                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext() , "Email or password wrong" , Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    protected void onCancelled() {
                        super.onCancelled();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                };

                logintast.execute();


            }
        });

        ((Button) findViewById(R.id.insSignup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this , Registration.class);
                startActivity(it);
            }
        });
    }


}
