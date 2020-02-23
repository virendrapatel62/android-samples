package com.nvmicrosoftwares.phonecall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
//                    Permission is not granted

                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.SEND_SMS)) {

                        Toast.makeText(MainActivity.this,
                                "Allow this", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.SEND_SMS}, 1);
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.SEND_SMS},
                                1

                        );
                        Toast.makeText(MainActivity.this,
                                "Requisting", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    // Permission has already been granted
                    String s = ((EditText) findViewById(R.id.editText)).getText().toString();
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(s, null, "Hellow User", null, null);
                }
            }
        });

        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
//                    Permission is not granted

                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.CALL_PHONE)) {

                        Toast.makeText(MainActivity.this,
                                "Allow this", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE}, 0);
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                0);
                        Toast.makeText(MainActivity.this,
                                "Requisting", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    // Permission has already been granted
                    String s = ((EditText) findViewById(R.id.editText)).getText().toString();
                    Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + s));
                    startActivity(i);
                }
            }
        });
    }


            @Override
            public void onRequestPermissionsResult(int requestCode,
                                                   String permissions[], int[] grantResults) {
                switch (requestCode) {
                    case 0: {
                        // If request is cancelled, the result arrays are empty.
                        if (grantResults.length > 0
                                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            String s = ((EditText) findViewById(R.id.editText)).getText().toString();
                            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + s));
                            startActivity(i);
                        } else {
                            Toast.makeText(MainActivity.this, "Deny", Toast.LENGTH_SHORT).show();
                        }
                    }
                    case 1: {
                        // If request is cancelled, the result arrays are empty.
                        if (grantResults.length > 0
                                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            String s = ((EditText) findViewById(R.id.editText)).getText().toString();
                            SmsManager sms = SmsManager.getDefault();
                            sms.sendTextMessage(s, null, "Hellow User", null, null);

                        } else {
                            Toast.makeText(MainActivity.this, "Deny", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }


        }

