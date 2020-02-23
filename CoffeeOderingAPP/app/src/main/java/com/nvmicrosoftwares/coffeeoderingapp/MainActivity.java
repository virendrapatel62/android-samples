package com.nvmicrosoftwares.coffeeoderingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    ImageView im1 , im2 , im3  , im4 , ip1 , ip2 , ip3 , ip4;
    TextView i1 , i2 , i3 , i4;

    int p1 = 25;
    int p2 = 26;
    int p3 = 28;
    int p4 = 29;
    Integer total =0;
    TextView totalEdit ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        im1 = findViewById(R.id.item1minus);
        totalEdit = findViewById(R.id.total);
        im2 = findViewById(R.id.item2minus);
        im3 = findViewById(R.id.item3minus);
        im4 = findViewById(R.id.item4minus);
        ip1 = findViewById(R.id.item1plus);
        ip2 = findViewById(R.id.item2plus);
        ip3 = findViewById(R.id.item3plus);
        ip4 = findViewById(R.id.item4plus);



        totalEdit.setText("0 RS.");

        i1 = findViewById(R.id.item1);
        i2 = findViewById(R.id.item2);
        i3 = findViewById(R.id.item3);
        i4 = findViewById(R.id.item4);

        i1.setText("0");
        i2.setText("0");
        i3.setText("0");
        i4.setText("0");

        View.OnClickListener ol  = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(v== ip1){
                   total = total + (p1);
                    i1.setText(""+(Integer.parseInt(i1.getText().toString())+1));
                }
                if(v== ip2){
                    total = total + (p2);
                    i2.setText(""+(Integer.parseInt(i2.getText().toString())+1));
                }
                if(v== ip3){
                    i3.setText(""+(Integer.parseInt(i3.getText().toString())+1));
                    total = total + (p3);
                }
                if(v== ip4){
                    i4.setText(""+(Integer.parseInt(i4.getText().toString())+1));
                    total = total + (p4);
                }

                // minus

                if(v== im1){
                    total = total - (p1);
                    i1.setText(""+(Integer.parseInt(i1.getText().toString())-1));
                }
                if(v== im2){
                    total = total - (p2);
                    i2.setText(""+(Integer.parseInt(i2.getText().toString())-1));
                }
                if(v== im3){
                    i3.setText(""+(Integer.parseInt(i3.getText().toString())-1));
                    total = total - (p3);
                }
                if(v== im4){
                    i4.setText(""+(Integer.parseInt(i4.getText().toString())-1));
                    total = total - (p4);
                }

                totalEdit.setText(total+" Rs");

            }
        };

        im1.setOnClickListener(ol);
        im2.setOnClickListener(ol);
        im3.setOnClickListener(ol);
        im4.setOnClickListener(ol);
        ip1.setOnClickListener(ol);
        ip2.setOnClickListener(ol);
        ip3.setOnClickListener(ol);
        ip4.setOnClickListener(ol);
        i1.setOnClickListener(ol);
        i2.setOnClickListener(ol);
        i3.setOnClickListener(ol);
        i4.setOnClickListener(ol);
    }
}
