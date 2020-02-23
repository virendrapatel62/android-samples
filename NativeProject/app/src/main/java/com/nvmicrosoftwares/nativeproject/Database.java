package com.nvmicrosoftwares.nativeproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.nvmicrosoftwares.nativeproject.models.MessageTypes;
import com.nvmicrosoftwares.nativeproject.models.UrgentCaller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Database {
    public static String DB_NAME = "MYDB6";

    public static void createTableDriving(Context context){
        String sql = "CREATE TABLE if not exists [driving]([driving] BOOLEAN NOT NULL);";
        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME , context.MODE_PRIVATE , null);
        db.execSQL(sql);
        db.close();
    }

    public static void createTableUrgentCallers(Context context){
        String sql = "CREATE TABLE if not exists [UrgentCallers](" +
                "  [name] VARCHAR NOT NULL, " +
                "  [number] VARCHAR NOT NULL UNIQUE, " +
                "  [id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL);";

        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME , context.MODE_PRIVATE , null);
        db.execSQL(sql);
        db.close();
    }
    public static void createTableCodes(Context context){
        String sql = "create table if not exists codes (code varchar not null)";
        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME , context.MODE_PRIVATE , null);
        db.execSQL(sql);
        db.close();
        Log.d("infooooo", "createTableCodes: Created Code Table" );
    }

    public static String insertIntoCodes(Context context){
        String s = "ABCDHSKDJKDSKNCKURYEIEFJDKF";
        String code = "";
        Random r = new Random();
        while(code.length() < 6){
            int aa = r.nextInt(s.length()-1);
            code = code + String.valueOf(s.charAt(aa));
        }
        String sql = "insert into codes (code) values (?)";
        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME , context.MODE_PRIVATE , null);
        db.execSQL(sql , new String[]{code});
        db.close();
        Log.d("infooooo", "createTableCodes: Insered Code Table" );
        return code;
    }

    public static boolean isCodeValid(Context context , String code){
        Boolean flag = false;
        try {
            String sql = "select * from codes where code = ?";
            SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME, context.MODE_PRIVATE, null);
            Cursor c = db.rawQuery(sql , new String[]{code.toUpperCase()});
            while(c.moveToNext()){
               flag = true;
            }
            db.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return flag;
    }

    public static void deleteFromCodes(Context context){
        String sql = "delete from codes ";
        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME , context.MODE_PRIVATE , null);
        db.execSQL(sql);
        db.close();
        Log.d("infooooo", "createTableCodes: Delted from codes" );
    }
    public static void createMessageTable(Context context){
        String sql = "CREATE TABLE if not exists [messages]( " +
                "  [id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, " +
                "  [message] VARCHAR NOT NULL)";
        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME , context.MODE_PRIVATE , null);
        db.execSQL(sql);
        db.close();
        inserIntoMessages(context , "Hyy !! I am Driving Now , Call me Later " , MessageTypes.Driving.getId());
        inserIntoMessages(context , "true" , MessageTypes.PlayMusicOnly.getId());
        inserIntoMessages(context , "true" , MessageTypes.VibrateOnly.getId());
        inserIntoMessages(context , "false" , MessageTypes.EmailMe.getId());
        inserIntoMessages(context , "" , MessageTypes.YourEmail.getId());
        Log.d("Info" , "Messages Table Created");
        Log.d("Info" , "All Messages" + selectAllFromMessages(context));
    }

    public void saveUrgentMessageKey(Context context , String key ){
        String message  = "if you Have Really Any Urgent To talk me ,  type '"+key+"' and send me a text message";
        if(getMessageById(context , MessageTypes.UrgentKey.getId()).isEmpty()){
            inserIntoMessages(context , message , MessageTypes.UrgentKey.getId());
        }else{
            updateMessage(context , message , MessageTypes.UrgentKey.getId());
        }
    }

    public static boolean inserIntoMessages(Context context , String message, Integer id){

        try {
            String sql = "insert INTO messages (message , id) VALUES (? , ?);";
            SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME, context.MODE_PRIVATE, null);
            db.execSQL(sql, new Object[]{message, id});
            db.close();

            Log.d("info" , "Inserted Data in Messages Table");

        }catch(Exception ex){
            ex.printStackTrace();
            return  false;
        }

        return true;
    }

    public static boolean updateMessage(Context context , String message, Integer id){

        try {
            String sql = "update messages set message = ? where id = ? ";
            SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME, context.MODE_PRIVATE, null);
            db.execSQL(sql, new Object[]{message, id});
            db.close();
            Log.d("info" , "Updated Data in Messages Table");
            Toast.makeText(context, "Message Updated...", Toast.LENGTH_SHORT).show();
        }catch(Exception ex){
            ex.printStackTrace();
            return  false;
        }
        return true;
    }

    public static String getMessageById(Context context , Integer id){
        String messsage = "";
        try {
            String sql = "select message from messages where id = ?";
            SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME, context.MODE_PRIVATE, null);
            Cursor c = db.rawQuery(sql , new String[]{id.toString()});
            //                Toast.makeText(context, "Inserted Caller", Toast.LENGTH_SHORT).show();
            while(c.moveToNext()){
                messsage = c.getString(0);
            }
            db.close();
            Log.d("info" , id+ " " +messsage  + "#########");
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return messsage;
    }

    public static ArrayList<String> selectAllFromMessages(Context context){
        ArrayList<String> l = new ArrayList<>();
        String sql = "select id , message from messages";
        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME , context.MODE_PRIVATE , null);
        Cursor c = db.rawQuery(sql , null);
        while(c.moveToNext()){
            l.add(c.getString(1));

            Log.d("info", "==============================================");
            Log.d("info", "selectAllFromMessages: "+ c.getInt(0));
            Log.d("info", "selectAllFromMessages: "+ c.getString(1));
            Log.d("info", "==============================================");
        }
        db.close();

        return l;
    }

    public static ArrayList<UrgentCaller> selectAllFromUrgentCallers(Context context){
        ArrayList<UrgentCaller> l = new ArrayList<>();
        String sql = "select id , name , number from UrgentCallers";
        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME , context.MODE_PRIVATE , null);
        Cursor c = db.rawQuery(sql , null);
        while(c.moveToNext()){
           UrgentCaller u = new UrgentCaller(c.getInt(0) , c.getString(1) , c.getString(2));
            l.add(u);
        }

//        Toast.makeText(context, ""+l, Toast.LENGTH_SHORT).show();
        db.close();

        return l;
    }

    public static boolean inserIntoUrgentCallers(Context context , String name , String number){
            try {
                String sql = "insert INTO UrgentCallers (name , number) VALUES (? , ?);";
                SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME, context.MODE_PRIVATE, null);
                db.execSQL(sql, new String[]{name, number});
//                Toast.makeText(context, "Inserted Caller", Toast.LENGTH_SHORT).show();
                db.close();
            }catch(Exception ex){
                ex.printStackTrace();
                return  false;
            }

            return true;
    }

    public static boolean deleteFromUrgentCallers(Context context , UrgentCaller uc){
            try {
                String sql = "delete from UrgentCallers WHERE id = "+uc.getId();
                SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME, context.MODE_PRIVATE, null);
                db.execSQL(sql);
//                Toast.makeText(context, "Removed Caller", Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, "Removed Caller"+ selectAllFromUrgentCallers(context), Toast.LENGTH_SHORT).show();
                db.close();
            }catch(Exception ex){
                ex.printStackTrace();
                return  false;
            }

            return true;
    }

    public static boolean isUrgent(String number , Context context){
        boolean flag = false;
        String sql = "select id , name , number from UrgentCallers where number='"+number+"'";
        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME , context.MODE_PRIVATE , null);
        Cursor c = db.rawQuery(sql , null);
        while(c.moveToNext()){
            Toast.makeText(context, "found", Toast.LENGTH_LONG).show();
           flag = true;
        }
        if(!flag){
            Toast.makeText(context, "Not found", Toast.LENGTH_LONG).show();
            return false;
        }

        db.close();
        return flag;


    }


    public static void IMDriving(Context context){
        String sql = "insert INTO driving (driving) VALUES('true')";
        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME , context.MODE_PRIVATE , null);
        db.execSQL(sql);
//        Toast.makeText(context, "Inserted", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public static void emailMe(Context context){
        updateMessage(context , "true" , MessageTypes.EmailMe.getId() );
    }

    public static void dontEmailMe(Context context){
        updateMessage(context , "false" , MessageTypes.EmailMe.getId() );
    }

    public static boolean isEmailActive(Context context){
        return Boolean.valueOf(getMessageById(context , MessageTypes.EmailMe.getId() ));
    }

    public static String getEmail(Context context){
        String email = "";
        email = getMessageById(context , MessageTypes.YourEmail.getId());
        return email;
    }
    public static void saveEmail(Context context , String email){
        updateMessage(context , email , MessageTypes.YourEmail.getId() );
        Toast.makeText(context, "Email Updated...", Toast.LENGTH_SHORT).show();
    }



    public static void IMNotDriving(Context context){
        String sql = "delete from driving;";
        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME , context.MODE_PRIVATE , null);
        db.execSQL(sql);
//        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public static boolean isDriving(Context context){
        String sql = "select * from driving;";
        SQLiteDatabase db = context.openOrCreateDatabase(DB_NAME , context.MODE_PRIVATE , null);
        Cursor c = db.rawQuery(sql , null);
        boolean flag = false;
        if(c.moveToNext()){
            flag = true;
            Toast.makeText(context, "Yes you are", Toast.LENGTH_SHORT).show();
        }
        db.close();

        return flag;
    }

    public static boolean vibrate(Context context){
        String s = getMessageById(context , MessageTypes.VibrateOnly.getId());
        return Boolean.valueOf(s);
    }

    public static boolean sound(Context context){
        String s = getMessageById(context , MessageTypes.PlayMusicOnly.getId());
        return Boolean.valueOf(s);
    }

    public static boolean soundAndVibrate(Context context){
        return sound(context) && vibrate(context);
    }


}
