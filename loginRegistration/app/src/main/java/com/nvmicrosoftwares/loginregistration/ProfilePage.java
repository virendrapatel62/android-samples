package com.nvmicrosoftwares.loginregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.nvmicrosoftwares.loginregistration.databasehandlers.StudentDatabaseHandler;
import com.nvmicrosoftwares.loginregistration.models.Student;

public class ProfilePage extends AppCompatActivity {

    TextView name , email , address , college;

    private String  userPassword ,  userEmail ;
    Student s = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        name = findViewById(R.id.name);
        email= findViewById(R.id.email);
        address = findViewById(R.id.address);
        college = findViewById(R.id.college);

        Intent intent = getIntent();


        userEmail = intent.getStringExtra("email");
        userPassword = intent.getStringExtra("password");
        setData();

    }

    private void setData(){
        s = Student.get(userEmail , openOrCreateDatabase(StudentDatabaseHandler.DBNAME , MODE_PRIVATE , null));

        Log.e("Student object" , s.toString());
         name.setText("Hello , " + s.getName());
        email.setText("Your Email is "+s.getEmail());
        address.setText("Your Address is "+ s.getAddress());
        college.setText("Your College Name is "+ s.getCollege());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_page_menu , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.edit){
            Intent it = new Intent(getApplicationContext() , EditProfile.class);
            it.putExtra("email" , userEmail);
            it.putExtra("password" , userPassword);
            startActivity(it);
        }
        return super.onOptionsItemSelected(item);
    }
}
