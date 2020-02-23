package com.nvmicrosoftwares.gridbuysell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> laptops , mobils;

    ListView listView;
    ArrayList<String> mobileDesc , laptopDesc , options;
    ArrayList<Integer> dataImage;
    ArrayList<String> datadesc;
    LayoutInflater inflater;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAll();
        inflater = LayoutInflater.from(getApplicationContext());
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return datadesc.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = inflater.inflate(R.layout.list_layout , null);
                ImageView im = view.findViewById(R.id.imageView);
                im.setImageResource(dataImage.get(position));

                TextView text = view.findViewById(R.id.textView);
                text.setText(datadesc.get(position));
                return  view;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String i = (String)options.get(position);
                if(i.equalsIgnoreCase("laptops")){
                    datadesc = laptopDesc;
                    dataImage = laptops;
                }else{
                    dataImage = mobils;
                    datadesc = mobileDesc;
                }

                BaseAdapter s = (BaseAdapter)listView.getAdapter();
                s.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initAll(){

        spinner = findViewById(R.id.spinner);
        laptopDesc = new ArrayList<>();
        mobileDesc = new ArrayList<>();
        laptops = new ArrayList<>();
        mobils = new ArrayList<>();
        options = new ArrayList<>();
        listView = findViewById(R.id.list);

        datadesc = laptopDesc;
        dataImage = laptops;

        options.add("Laptops");
        options.add("Mobiles");
        spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext() , android.R.layout.simple_spinner_dropdown_item , options));

        mobils.add(R.raw.ma);
        mobils.add(R.raw.mb);
        mobils.add(R.raw.mc);
        mobils.add(R.raw.md);
        mobils.add(R.raw.me);

        mobileDesc.add("Samsung J2");
        mobileDesc.add("Samsung J3");
        mobileDesc.add("Samsung J4");
        mobileDesc.add("Samsung J5");
        mobileDesc.add("Samsung J6");

        laptops.add(R.raw.la);
        laptops.add(R.raw.lb);
        laptops.add(R.raw.lc);
        laptops.add(R.raw.ld);
        laptops.add(R.raw.le);

        laptopDesc.add("Dell 1");
        laptopDesc.add("Hp 1");
        laptopDesc.add("Dell 2");
        laptopDesc.add("Dell 4");
        laptopDesc.add("Dell 5");

    }
}
