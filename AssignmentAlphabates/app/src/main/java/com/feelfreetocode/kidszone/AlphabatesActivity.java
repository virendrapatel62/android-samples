package com.feelfreetocode.kidszone;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class AlphabatesActivity extends AppCompatActivity {

    ViewPager pager;
    Slider slider ;
    int numberOfPages = 26;
    ArrayList<Integer> images = new ArrayList<>();
    ArrayList<String> desc = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabets);

        setData();
        pager = findViewById(R.id.pager);
        slider = new Slider(getSupportFragmentManager());

        pager.setAdapter(slider);

    }

    private void setData(){

        desc.add("A For Apple");
        desc.add("B For Bee");
        desc.add("C For Cat");
        desc.add("D For Drum");
        desc.add("E For Elephant");
        desc.add("F For Fish");
        desc.add("G For Gluphs");
        desc.add("H For Home");
        desc.add("I For Igloo");
        desc.add("J For Jug");
        desc.add("K For Key");
        desc.add("L For Lock");
        desc.add("M For Mug");
        desc.add("N For Net");
        desc.add("O For Orange");
        desc.add("P For Pumpkin");
        desc.add("Q For Queen");
        desc.add("R For Robot");
        desc.add("S For Snack");
        desc.add("T For Thurmos");
        desc.add("U For Umbrella");
        desc.add("V For Volcno");
        desc.add("W For Watch");
        desc.add("X For Xray");
        desc.add("Y For Yo-Yo");
        desc.add("Z For Zip");


        images.add(R.mipmap.a);
        images.add(R.mipmap.b);
        images.add(R.mipmap.c);
        images.add(R.mipmap.d);
        images.add(R.mipmap.e);
        images.add(R.mipmap.f);
        images.add(R.mipmap.g);
        images.add(R.mipmap.h);
        images.add(R.mipmap.i);
        images.add(R.mipmap.j);
        images.add(R.mipmap.k);
        images.add(R.mipmap.l);
        images.add(R.mipmap.m);
        images.add(R.mipmap.n);
        images.add(R.mipmap.o);
        images.add(R.mipmap.p);
        images.add(R.mipmap.q);
        images.add(R.mipmap.r);
        images.add(R.mipmap.s);
        images.add(R.mipmap.t);
        images.add(R.mipmap.u);
        images.add(R.mipmap.v);
        images.add(R.mipmap.w);
        images.add(R.mipmap.x);
        images.add(R.mipmap.y);
        images.add(R.mipmap.z);
        images.add(R.mipmap.z);

    }

    class Slider extends FragmentStatePagerAdapter{


        public Slider(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            return AlphabatesFragment.newInstance( desc.get(i) , images.get(i)  , getApplicationContext())  ;
        }

        @Override
        public int getCount() {
            return numberOfPages;
        }
    }
}
