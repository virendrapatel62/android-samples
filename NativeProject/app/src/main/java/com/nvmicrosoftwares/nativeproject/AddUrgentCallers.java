package com.nvmicrosoftwares.nativeproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddUrgentCallers extends AppCompatActivity {


    Button save ;
    EditText name , number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_urgent_callers);


        initall();
    }

    private void initall(){
        save = findViewById(R.id.save);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nm = name.getText().toString().trim();
                String num = number.getText().toString().trim();
                if(num.length()!=10){
                    Toast.makeText(AddUrgentCallers.this, "Number must be in 10 in lenth", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(nm.isEmpty()){
                    Toast.makeText(AddUrgentCallers.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(Database.inserIntoUrgentCallers(getApplicationContext() , nm , "+91"+num)){
                    number.setText("");
                    name.setText("");
                }else{
                    Toast.makeText(AddUrgentCallers.this, "Number already exists  or Something went wrong", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}

