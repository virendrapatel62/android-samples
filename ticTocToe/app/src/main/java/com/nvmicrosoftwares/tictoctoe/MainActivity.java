package com.nvmicrosoftwares.tictoctoe;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    EditText text;
    int n1 ;
    String num1="" , num2=""  ;
    int n2;
    String op="";
    int ans ;
    TextToSpeech tts ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        tts = new TextToSpeech(this , this);
        tts.speak("Wellcome to Speaking Calculator" , TextToSpeech.QUEUE_FLUSH ,null);
    }

    @Override
    public void onInit(int status) {

    }

    public void clc(View view){
        Button b = (Button)view;
        String s = b.getText().toString();



        if(b.getText().toString().equalsIgnoreCase("reset")){
            num1 = "";
            num2 = "";
            op = "";
            text.setText("");
            return ;
        }


        if(s.equals("+") || s.equals("/") || s.equals("*") || s.equals("-")){
            op = b.getText().toString();
            tts.speak(op , TextToSpeech.QUEUE_FLUSH ,null);
        }else if(s.equals("=")){
            int ans = 0;
            // show answer
            if(op.equals("+")){
                ans = Integer.parseInt(num1) + Integer.parseInt(num2);
            }else if(op.equals("-")){
                ans = Integer.parseInt(num1) - Integer.parseInt(num2);
            }
            else if(op.equals("*")){
                ans = Integer.parseInt(num1) * Integer.parseInt(num2);
            }
            else if(op.equals("/")){
                ans = Integer.parseInt(num1) / Integer.parseInt(num2);
            }
            text.setText(num1+ " "+ op+" "+num2 +" = " + ans);
            tts.speak( num1 +" "+ op+" " +num2 + " = " + ans  , TextToSpeech.QUEUE_FLUSH ,null);
            return ;

        }else{

            if(op.isEmpty()){
                num1 = num1+s;
                tts.speak(num1 , TextToSpeech.QUEUE_FLUSH ,null);
            }else{
                num2 = num2+s;
                tts.speak(num2 , TextToSpeech.QUEUE_FLUSH ,null);
            }
        }
        text.setText(num1+ " "+ op+" "+num2 );



    }
}
