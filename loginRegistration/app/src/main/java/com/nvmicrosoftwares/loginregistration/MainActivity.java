package com.nvmicrosoftwares.loginregistration;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nvmicrosoftwares.loginregistration.databasehandlers.StudentDatabaseHandler;
import com.nvmicrosoftwares.loginregistration.models.Student;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText email , password;
    TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        Student.createTable(openOrCreateDatabase(StudentDatabaseHandler.DBNAME ,MODE_PRIVATE, null));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if(em.isEmpty()){
                    email.setError("Enter Email");
                    return ;
                }

                if(pass.isEmpty()){
                    password.setError("Enter Password");
                    return ;
                }

                Student s = new Student();
                s.setEmail(em);
                s.setPassword(pass);
                if(s.login(openOrCreateDatabase(StudentDatabaseHandler.DBNAME , MODE_PRIVATE , null))){
                    Toast.makeText(getApplicationContext() , "Logged in Successfully " + s.getName() , Toast.LENGTH_LONG).show();
                    Intent it = new Intent(getApplicationContext() , ProfilePage.class);
                    it.putExtra("email" , s.getEmail());
                    it.putExtra("password" , s.getPassword());
                    startActivity(it);
                }else{
                    password.setError("Wrong Password Or Email");
                    email.setError("Wrong Password Or Email");
                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , Signup.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.login){
            Toast.makeText(getApplicationContext() , "You are in login Screen" , Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(getApplicationContext() , Signup.class);
            startActivity(intent);
        }
       return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        email.setText("");
        password.setText("");
    }

    private void init(){
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
    }
}
