package com.nvmicrosoftwares.spinnersample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends BaseAdapter {

    List<Integer> pics ;
    List<String> names;
    LayoutInflater inflater;
    public StudentAdapter(Context app , List<Integer> pics, List<String> names){
       inflater =  LayoutInflater.from(app);
        this.names = names;
        this.pics = pics;

    }
    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.spinner_layout , null);
        ImageView img = view.findViewById(R.id.imageView);
        img.setImageResource(pics.get(position));

        TextView name = view.findViewById(R.id.textView);
        name.setText(names.get(position));

        return  view;
    }
}
