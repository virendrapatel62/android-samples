package com.feelfreetocode.kidszone;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class Numbers extends AppCompatActivity {

    int numberOfPages = 10;
    ViewPager pager;
    Slider slider ;
    ArrayList<Integer> images = new ArrayList<>();
    ArrayList<String> desc = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        pager = findViewById(R.id.pager);

        getData();

        slider = new Slider(getSupportFragmentManager());

        pager.setAdapter(slider);



    }

    private void getData(){
        images.add(R.mipmap.zero);
        images.add(R.mipmap.one);
        images.add(R.mipmap.two);
        images.add(R.mipmap.three);
        images.add(R.mipmap.four);
        images.add(R.mipmap.five);
        images.add(R.mipmap.six);
        images.add(R.mipmap.seven);
        images.add(R.mipmap.eight);
        images.add(R.mipmap.nine);

        desc.add("Zero");
        desc.add("One");
        desc.add("Two");
        desc.add("Three");
        desc.add("Four");
        desc.add("Five");
        desc.add("Six");
        desc.add("Seven");
        desc.add("Eight");
        desc.add("Nine");
    }

    class Slider extends FragmentStatePagerAdapter {


        public Slider(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            return AlphabatesFragment.newInstance( desc.get(i) , images.get(i) , getApplicationContext() )  ;
        }

        @Override
        public int getCount() {
            return numberOfPages;
        }
    }
}
