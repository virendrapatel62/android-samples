package com.nvmicrosoftwares.loginregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nvmicrosoftwares.loginregistration.databasehandlers.StudentDatabaseHandler;
import com.nvmicrosoftwares.loginregistration.models.Student;

public class EditProfile extends AppCompatActivity {

    private String  userPassword ,  userEmail ;
    EditText name , address , college , password ;
    Button update;
    Student s ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent intent = getIntent();
        s = new Student();

        userEmail = intent.getStringExtra("email");
        userPassword = intent.getStringExtra("password");

        s.setEmail(userEmail);
        s.setPassword(userPassword);
        s = Student.get(userEmail , openOrCreateDatabase(StudentDatabaseHandler.DBNAME  , MODE_PRIVATE , null));

        initAll();

        name.setText(s.getName());
        address.setText(s.getAddress());
        college.setText(s.getCollege());
        password.setText(s.getPassword());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nm = name.getText().toString().trim();
                String addr = address.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String col = college.getText().toString().trim();

                s.setName(nm);
                s.setAddress(addr);
                s.setPassword(pass);
                s.setCollege(col);
                if(s.updateProfile(openOrCreateDatabase(StudentDatabaseHandler.DBNAME , MODE_PRIVATE , null))){
                    Toast.makeText(EditProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                    Log.e("Name" , s.getName());
                    Log.e("EMail" , s.getEmail());
                    Log.e("Password" , s.getPassword());
                    Log.e("Address" , s.getAddress());
                    Log.e("Colleges" , s.getCollege());
                    finish();
                }else{
                    Toast.makeText(EditProfile.this, "Profile Couldnt update", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void  initAll(){
        name = findViewById(R.id.editName);
        address = findViewById(R.id.editAddress);
        college = findViewById(R.id.editCollege);
        update = findViewById(R.id.update);
        password = findViewById(R.id.editPassword);
    }
}
