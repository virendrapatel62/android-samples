package com.nvmicrosoftwares.nativeproject;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nvmicrosoftwares.nativeproject.settingScreens.ChangeEmail;
import com.nvmicrosoftwares.nativeproject.settingScreens.UpdatMessageText;
import com.nvmicrosoftwares.nativeproject.settingScreens.UrgentNotificationSettings;

import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class Settings extends AppCompatActivity {

    ViewPager viewPager ;
    PagerAdapter pagerAdapter;
    int i = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        viewPager = findViewById(R.id.pager);

        viewPager.setAdapter(new ScreenSlide(getSupportFragmentManager()));


        Toast.makeText(this, "Swipe For More Settings -> ->", Toast.LENGTH_LONG).show();


    }

    class ScreenSlide extends FragmentPagerAdapter{

        public ScreenSlide(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i){
                case 0:
                    return  new UpdatMessageText();
                case 1:
                    return  new UrgentNotificationSettings();
                case 2:
                    return  new ChangeEmail();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


}
