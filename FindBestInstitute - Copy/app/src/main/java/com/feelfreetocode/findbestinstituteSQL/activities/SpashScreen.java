package com.feelfreetocode.findbestinstituteSQL.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.feelfreetocode.findbestinstituteSQL.R;
import com.feelfreetocode.findbestinstituteSQL.activities.institute.InstituteHome;
import com.feelfreetocode.findbestinstituteSQL.activities.student.StudentHomeScreen;
import com.feelfreetocode.findbestinstituteSQL.models.Institute;
import com.feelfreetocode.findbestinstituteSQL.models.LoginInfo;
import com.feelfreetocode.findbestinstituteSQL.models.Student;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class SpashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash_screen);
        getWindow().setStatusBarColor(getResources().getColor(R.color.actionBarColorForSplash));
        slash();

    }

    @Override
    protected void onResume() {
        slash();
        super.onResume();
    }

    private void slash(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    try {
                        FileInputStream fis = openFileInput("login");
                        ObjectInputStream oos = new ObjectInputStream(fis);
                        LoginInfo loginInfo = (LoginInfo)oos.readObject();
                        Log.e("splashscreen" , loginInfo+"");
                        if(loginInfo== null){
                            throw  new RuntimeException();
                        }
                        if(loginInfo.getType().equalsIgnoreCase("student")){
                            ifSTudent();
                        }else{
                            ifInstitute();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Intent i = new Intent(getApplicationContext() , LoginByScrenn.class);
                        startActivity(i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void ifSTudent(){
        Intent i = new Intent(getApplicationContext(), StudentHomeScreen.class);
        Log.e("splashscreen" , "This is Student");


        try{
            Log.e("splashScreen" , "1");
            FileInputStream is = openFileInput("studentUser");
            Log.e("splashScreen" , "2");
            ObjectInputStream os= new ObjectInputStream(is);
            Log.e("splashScreen" , "3");
            Student s  = (Student)os.readObject();
            Log.e("splashScreen" , s+"");
            if(s== null){
                throw  new RuntimeException();
            }
            i.putExtra("student" , s);

            startActivity(i);
        }catch(Exception ex){
            Log.e("splash" , ex.getLocalizedMessage());
            Intent ii = new Intent(getApplicationContext(), LoginByScrenn.class);
            startActivity(ii);
        }

    }

    private void ifInstitute(){
        Intent i = new Intent(getApplicationContext(), InstituteHome.class);
        Log.e("splashscreen" , "This is institute");


        try{
            Log.e("splashScreen" , "1");
            FileInputStream is = openFileInput("instituteUser");
            Log.e("splashScreen" , "2");
            ObjectInputStream os= new ObjectInputStream(is);
            Log.e("splashScreen" , "3");
            Institute s  = (Institute) os.readObject();
            Log.e("splashScreen" , s+"");
            if(s== null){
                throw  new RuntimeException();
            }
            i.putExtra("institute" , s);

            startActivity(i);
        }catch(Exception ex){
            Log.e("splash" , ex.getLocalizedMessage());
            Intent ii = new Intent(getApplicationContext(), LoginByScrenn.class);
            startActivity(ii);
        }

    }

}
