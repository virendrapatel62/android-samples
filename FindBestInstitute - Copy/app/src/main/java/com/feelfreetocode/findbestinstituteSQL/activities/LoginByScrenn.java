package com.feelfreetocode.findbestinstituteSQL.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.feelfreetocode.findbestinstituteSQL.R;
import com.feelfreetocode.findbestinstituteSQL.activities.institute.LoginActivity;
import com.feelfreetocode.findbestinstituteSQL.activities.student.StudentLogin;

public class LoginByScrenn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_by_screen);

        findViewById(R.id.student).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginByScrenn.this ,StudentLogin.class );
                startActivity(i);
            }
        });

        findViewById(R.id.institute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginByScrenn.this ,LoginActivity.class );
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
        moveTaskToBack(true);
    }
}
