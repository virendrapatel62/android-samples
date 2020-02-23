package com.nvmicrosoftwares.appmotivation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager pager ;
    PagerAdapter adapter ;
    ArrayList<Fragment> fragments;

    ArrayList<MyQuote> quotes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragments = new ArrayList<>();

        pager = findViewById(R.id.pager);
        String path = getResources().getResourceName(R.raw.quotes);
        InputStream is = getResources().openRawResource(R.raw.quotes);
        BufferedInputStream bi = new BufferedInputStream(is);
        byte[] b = new byte[1024];
        try {
            String line = "";
            String data = "";
            while ((bi.read(b)) != -1) {
                data = data + new String(b);
                Log.e("infooo : ", data);
            }


            JSONArray arr = new JSONArray(data);

            System.err.println("arr : " + arr);

            for(int k = 0 ; k <arr.length() ; k  ++){
                JSONObject obj = arr.getJSONObject(k);
                System.err.println(obj.get("text"));
                System.err.println(obj.get("from"));
                MyQuote myQuote = new MyQuote(obj.get("text").toString()  , obj.get("from").toString());
                Quotes q = new Quotes();
                q.setQuote(myQuote , k);
                fragments.add(q);
                System.err.println("+=================================");
            }
        }catch(Exception ex){
            Log.e("infooo : ", "Error");
        }

        adapter = new QuoteAdapter(getSupportFragmentManager() , fragments);
        pager.setAdapter(adapter);

    }


    public class QuoteAdapter extends FragmentStatePagerAdapter{

        ArrayList<Fragment>  fragments  ;

        public QuoteAdapter(FragmentManager fm , ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int i) {

            return fragments.get(i);
        }

        @Override
        public int getCount()
        {
            return fragments.size();
        }
    }
}
