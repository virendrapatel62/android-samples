package com.nvmicrosoftwares.openwhatsapp;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = ((EditText)findViewById(R.id.editText)).getText().toString();
                String num = ((EditText)findViewById(R.id.editText2)).getText().toString();

                Intent sendIntent =
                        new Intent(Intent.ACTION_SEND);
                sendIntent.setPackage("com.whatsapp");
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
                sendIntent.putExtra("jid" ,
                        num + "@s.whatsapp.net" );
                startActivity(sendIntent);
            }
        });
    }
}
