package com.nvmicrosoftwares.edittextsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name , password ;
    Button save;
    TextView t1 , t2 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        save = findViewById(R.id.save);
        password = findViewById(R.id.password);
        t1 = findViewById(R.id.textView);
        t2 = findViewById(R.id.textView2);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String nm =   name.getText().toString() ;
              String pass = password.getText().toString();
              t1.setText(nm);
              t2.setText(pass);

            }
        });
    }
}
