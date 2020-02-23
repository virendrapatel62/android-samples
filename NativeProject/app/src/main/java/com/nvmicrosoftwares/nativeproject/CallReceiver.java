package com.nvmicrosoftwares.nativeproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.se.omapi.Session;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


import com.nvmicrosoftwares.nativeproject.models.MessageTypes;

import java.lang.reflect.Method;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.prefs.Preferences;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CallReceiver extends BroadcastReceiver {
     Preferences preferences ;
     int i = 0;
     String msg;
     public static AudioManager audioManager;
     public static MediaPlayer mediaPlayer;
     public static TimerTask timerTask;
     public static Timer timer;
        public static  Vibrator vibrator;
    Context context;

    public CallReceiver() {
        super();
    }

    public static Vibrator getVibrator() {
        return vibrator;
    }

    public static AudioManager getAudioManager() {
        return audioManager;
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static java.util.Timer getTimer() {
        return timer;
    }

    @Override
    public void onReceive(Context context, Intent intent) {


         audioManager  = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
         mediaPlayer = MediaPlayer.create(context , R.raw.music);
        preferences = Preferences.systemRoot();
        this.context = context;
              if(Database.isDriving(context) &&
                intent.getAction().equals("android.intent.action.PHONE_STATE")){
                    Toast.makeText(context, "Count: " + (i++), Toast.LENGTH_SHORT).show();
                    String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    SmsManager manager = SmsManager.getDefault();
                    Toast.makeText(context, Database.isUrgent(number , context)+"----"+number, Toast.LENGTH_LONG).show();
                    if(!Database.isUrgent(number , context)) {
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                        try {
                            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
                            if (telephonyManager.getCallState() == TelephonyManager.CALL_STATE_IDLE) {

                                msg = Database.getMessageById(context , MessageTypes.Driving.getId()) + " " +
                                        " if you Have Really Any Urgent To talk me ,  type '"+ Database.insertIntoCodes(context)+"' and send me a text message";
                                Log.d("infooooo", "onReceive: cutted " + i++);
                                Log.d("infooooo", "onReceive: cutted " + msg);
                                manager.sendTextMessage(number, null, msg, null, null);
                            }
                        } catch (Exception ex) {
                            Toast.makeText(context, "Exception", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                // when email on
                if(Database.isEmailActive(context) &&
                    intent.getAction().equals("android.intent.action.PHONE_STATE") ){
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
                    if (telephonyManager.getCallState() == TelephonyManager.CALL_STATE_RINGING) {

                        String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                        String email = Database.getEmail(context);

                        if (!email.isEmpty() && !number.isEmpty()) {
                            Log.d("infoooo", "onReceive: Sendign email to  " + email + "  --  " +"You Have A new Call From " + number );
                            send(email, "You Have A new Call From " + number);
                        }
                    }
                }

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            SmsMessage[] msgs =  Telephony.Sms.Intents.getMessagesFromIntent(intent);
            Toast.makeText(context, "Message Received", Toast.LENGTH_SHORT).show();
            for(SmsMessage sms:msgs){
                final String message = sms.getMessageBody().toUpperCase();
                Boolean flag = Database.isCodeValid(context , message);
                if(flag){

                    // Change mode to general
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    Toast.makeText(context, "Yesss", Toast.LENGTH_SHORT).show();

                    vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    // Start Vibration
                    if(Database.soundAndVibrate(context)){
                        vibrator.vibrate(new long[]{0l , 2000l , 1000} ,0 );
                        mediaPlayer.start();
                        showNotification(context);
                    }else if(Database.sound(context)){

                        mediaPlayer.start();
                        showNotification(context);
                    }else if(Database.vibrate(context)){
                        vibrator.vibrate(new long[]{0l , 2000l , 1000} ,0 );
                        showNotification(context);
                    }
                    // Stop Vibration

                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            vibrator.cancel();
                            mediaPlayer.stop();
                        }
                    };

                    timer = new Timer();
                    timer.schedule(timerTask , 20000);
                }
            }
        }

    }

    private void send(final String email ,final String msg){
        final AsyncTask<Object , Object, Object> task = new AsyncTask<Object, Object, Object>() {
            @Override
            protected Boolean doInBackground(Object... strings) {
                CallReceiver.sendMail(msg , email);
//                Toast.makeText(getApplicationContext() , "sent" , Toast.LENGTH_LONG).show();
                return true;
            }
        };


            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        if((Boolean)task.execute(null , null , null).get()){
                            Log.d("infooooo", "run: Sent.....");
                        }else{
                            Log.d("infooooo", "run: Not Sent.....");
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } , 15000);


    }

    private static void sendMail(String content , String to){
         Properties props = new Properties();
            props.put("mail.transport.protocol","smtp");
            props.put("mail.smtp.auth","true");
            props.put("mail.smtp.starttls.enable","true");
            props.put("mail.smtp.host","smtp.gmail.com");
            props.put("mail.smtp.port","587");
        javax.mail.Session session = javax.mail.Session.getInstance(props , new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("teamnahalo@gmail.com" , "nahalo@123");
            }
        });
        try{
            MimeMessage mm = new MimeMessage(session);

            mm.setFrom(new InternetAddress("teamnahalo@gmail.com"));
            mm.addRecipients(Message.RecipientType.TO , to);
            mm.setContent(content , "text/html" );

            Transport.send(mm);
        }catch(MessagingException ex){
            ex.printStackTrace();

        }
    }



    public void showNotification(Context context){
        Notification notification = new Notification.Builder(context).setTicker("Urgent Call")
                .setContentText("Hyy ! Its Urgent Call ....").
                        setContentTitle("Click TO Stop").setContentIntent(PendingIntent.getActivity(context, 0 , new Intent(context , MainActivity.class), 0)).
                        setSmallIcon(R.drawable.add).getNotification();

        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0 , notification);
    }


    public static void stop(){
        if(vibrator != null){
            vibrator.cancel();
        }

        if(mediaPlayer!=null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }

}
