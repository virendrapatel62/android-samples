package com.nvmicrosoftwares.gridsamplewithimages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends BaseAdapter {

    private List<Integer> images ;
    private List<String> names ;
    private Context applicationContext;
    private LayoutInflater inflater;
    public  StudentAdapter(Context applicationContext , List<Integer> imageIds , List<String> names ){
        this.applicationContext = applicationContext;
        this.images = imageIds;
        this.names = names;

        inflater = LayoutInflater.from(applicationContext);
    }
    @Override
    public long getItemId(int position) {
        return 0;
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
        View view = inflater.inflate(R.layout.student_layout , null);
        ImageView img = view.findViewById(R.id.imageView);
        img.setImageResource(images.get(position));

        TextView name = view.findViewById(R.id.textView);
        name.setText(names.get(position));

        return view;
    }
}
