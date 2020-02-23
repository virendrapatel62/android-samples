package com.nvmicrosoftwares.nativeproject;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.Preference;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nvmicrosoftwares.nativeproject.models.MessageTypes;

import java.util.Observer;
import java.util.concurrent.ExecutionException;
import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    int i = 0;
    public static final String DRIVING = "DRIVING";
    TextToSpeech tts ;
    CheckBox checkBox , emailme ;
    LinearLayout add , remove , settings , show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAll();


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Database.IMDriving(getApplicationContext());
                }else{
                    Database.IMNotDriving(getApplicationContext());
                    Database.deleteFromCodes(getApplicationContext());
                }
            }
        });

        emailme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(Database.getEmail(getApplicationContext()).trim().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please Update Your Email First , Go to Setings", Toast.LENGTH_LONG).show();
                    emailme.setChecked(false);
                    return ;
                }
                if(isChecked){
                    Database.emailMe(getApplicationContext());
                }else{
                    Database.dontEmailMe(getApplicationContext());
                }
            }
        });

        emailme.setChecked(Database.isEmailActive(getApplicationContext()));
        checkBox.setChecked(Database.isDriving(getApplicationContext()));

        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intent);
        }

        CallReceiver.stop();

    }



    private void initAll(){
        tts = new TextToSpeech(this , this);

        Database.createTableDriving(getApplicationContext());
        Database.createTableUrgentCallers(getApplicationContext());
        Database.createMessageTable(getApplicationContext());
        Database.createTableCodes(getApplicationContext());

        checkBox = findViewById(R.id.driving);
        emailme = findViewById(R.id.emailme);

        add = findViewById(R.id.add);
        remove = findViewById(R.id.remove);
        settings = findViewById(R.id.settings);
        show = findViewById(R.id.show);


        View.OnClickListener ol = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = null;

                if(v==add){
                    intent = new Intent(MainActivity.this , AddUrgentCallers.class);
                }else if(v==show){
                    intent = new Intent(MainActivity.this , ShowAll.class);
                }else if(v==settings){
                    intent = new Intent(MainActivity.this , Settings.class);
                }else if(v==remove){
                    intent = new Intent(MainActivity.this , ShowAll.class);
                    intent.putExtra("msg" , "Long Press to Delete Contact");
                }
                if(intent!=null){
                    startActivity(intent);
                }

            }
        };

        add.setOnClickListener(ol);
        remove.setOnClickListener(ol);
        show.setOnClickListener(ol);
        settings.setOnClickListener(ol);

    }

    int temp = 0;

    @Override
    protected void onResume() {
//        if(temp ==0)
//            findViewById(R.id.lay).setBackgroundResource(R.mipmap.bg);
//        if(temp ==1)
//            findViewById(R.id.lay).setBackgroundResource(R.mipmap.bgg);
//        if(temp ==2)
//            findViewById(R.id.lay).setBackgroundResource(R.mipmap.bgggg);
//
//        temp++;
//        if(temp == 3){
//            temp = 0;
//        }
        super.onResume();
    }

    @Override
    public void onInit(int status) {

    }
}
