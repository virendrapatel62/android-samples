package com.nvmicrosoftwares.listviewithimages;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

public class StudentAdpater extends BaseAdapter {


    private List<String> names;
    private List<Integer> images;
    LayoutInflater inflater ;
    public StudentAdpater(Context applicationContext , List<Integer> images , List<String> names){
        inflater = LayoutInflater.from(applicationContext);
        this.images = images;
        this.names = names;
    }
    @Override
    public int getCount() {
        return  names.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= (View)inflater.inflate(R.layout.contact_layout , null);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView = view.findViewById(R.id.name);
        textView.setText(names.get(position));
        imageView.setImageResource(images.get(position));
        return view;
    }
}
