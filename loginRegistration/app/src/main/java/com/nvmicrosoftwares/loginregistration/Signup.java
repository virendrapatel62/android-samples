package com.nvmicrosoftwares.loginregistration;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nvmicrosoftwares.loginregistration.databasehandlers.StudentDatabaseHandler;
import com.nvmicrosoftwares.loginregistration.models.Student;

public class Signup extends AppCompatActivity {

    TextView login ;
    Button signup ;
    EditText name , email , password , address , college;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initAll();
        setOnclickListeners();
    }

    private void initAll(){
        login = findViewById(R.id.loginHere);
        name = findViewById(R.id.name);
        address= findViewById(R.id.address);
        email = findViewById(R.id.email);
        password = findViewById(R.id.Password);
        college = findViewById(R.id.college);
        signup = findViewById(R.id.signupbtn);
    }

    private void setOnclickListeners(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nm = name.getText().toString().trim();
                String em = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String addr = address.getText().toString().trim();
                String clg = college.getText().toString().trim();

                if(nm.isEmpty()){
                    name.setError("Enter Name");
                    return;
                }
                if(em.isEmpty()){
                    email.setError("Enter Email");
                    return;
                }
                if(addr.isEmpty()){
                    address.setError("Enter Address");
                    return;
                }
                if(clg.isEmpty()){
                    college.setError("Enter College Name");
                    return;
                }if(pass.isEmpty()){
                    password.setError("Enter Password");
                    return;
                }


                Student s = new Student();
                s.setName(nm);
                s.setEmail(em);
                s.setPassword(pass);
                s.setAddress(addr);
                s.setCollege(clg);
                if(s.save(openOrCreateDatabase(StudentDatabaseHandler.DBNAME , MODE_PRIVATE , null))){
                    Toast.makeText(Signup.this, "Signup Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_SEND , Uri.parse("smsto:"+addr));
                    intent.putExtra("sms_body" , "your password is "+ pass);
                    startActivity(Intent.createChooser(intent ,"Send using" ));
                    finish();
                }else{
                    Toast.makeText(Signup.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

}

