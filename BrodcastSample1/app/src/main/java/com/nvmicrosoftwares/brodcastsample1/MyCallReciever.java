package com.nvmicrosoftwares.brodcastsample1;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MyCallReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            Toast.makeText(context, "Calling", Toast.LENGTH_SHORT).show();
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);

            int state = tm.getCallState();
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                Toast.makeText(context, "Ringing...", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, intent.getStringExtra("incoming_number"), Toast.LENGTH_SHORT).show();
            } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                Toast.makeText(context, "No Call", Toast.LENGTH_SHORT).show();
            } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                Toast.makeText(context, "Active Call", Toast.LENGTH_SHORT).show();
            }
        }else if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            SmsMessage[] messges =  Telephony.Sms.Intents.getMessagesFromIntent(intent);

            for(SmsMessage m : messges){
                String ms = m.getMessageBody();
                String num = m.getOriginatingAddress();
                Toast.makeText(context, ms, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, num, Toast.LENGTH_SHORT).show();
                Log.e("Message"  , ms);
            }

        }
    }
}
