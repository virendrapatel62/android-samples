package com.nvmicrosoftwares.brodcastrecieverapplication;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MyReceiver extends BroadcastReceiver {
    private  static EditText editText;
    private static String phonenumber = "";
    private static int i = 0;
    private static ArrayList<String> arrayList;
    public  void setEditText(EditText editText){
        MyReceiver.editText = editText;
    }
    static{
        arrayList = new ArrayList<>();
        arrayList.add("198");
        arrayList.add("199");
        arrayList.add("9144460897");
        arrayList.add("123");
        arrayList.add("9144460897");
        arrayList.add("198");
        arrayList.add("199");
        arrayList.add("198");
        arrayList.add("9144460897");
        arrayList.add("198");
        arrayList.add("9144460897");
        arrayList.add("199");
        arrayList.add("9144460897");
        arrayList.add("121");
        arrayList.add("9144460897");
        arrayList.add("123");
        arrayList.add("9144460897");
        arrayList.add("9144460897");
        arrayList.add("9144460897");
        phonenumber = arrayList.get(0);
//        arrayList.add("198");
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            int state = tm.getCallState();

            String num = intent.getStringExtra("incoming_number");
//            Toast.makeText(context, "Ringing number : " + num, Toast.LENGTH_LONG).show();

            if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                Toast.makeText(context, "Active with  : " + num, Toast.LENGTH_LONG).show();
//                Toast.makeText(context, "Call Active", Toast.LENGTH_LONG).show();
            }


            if (state == TelephonyManager.CALL_STATE_IDLE) {
                Intent in = new Intent(Intent.ACTION_CALL);

                in.setData(Uri.parse("tel:" + phonenumber));
                phonenumber = arrayList.get(i++);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in);
                Toast.makeText(context, "Disconnected... ", Toast.LENGTH_LONG).show();
            }
        }
    }
}
