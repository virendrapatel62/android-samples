package com.nvmicrosoftwares.gridviewimagesexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

public class StudentAdapter extends BaseAdapter {

    Context applicationContext ;
    List<Integer> pics ;
    List<String> names;
    LayoutInflater layoutInflater;

    public StudentAdapter(Context  context, List<Integer> pics ,List<String> names) {
        this.applicationContext = context;
        this.pics = pics;
        this.names = names;
        layoutInflater = LayoutInflater.from(applicationContext);
    }
    @Override
    public int getCount() {
        return  names.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = (View)layoutInflater.inflate(R.layout.person_layout , null);
        ImageView imageView= view.findViewById(R.id.imageView);
        TextView textView= view.findViewById(R.id.name);
        imageView.setImageResource(pics.get(position));
        textView.setText(names.get(position));
        return view;
    }

    public void add(Integer imageid , String Name){
        pics.add(imageid);
        names.add(Name);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
