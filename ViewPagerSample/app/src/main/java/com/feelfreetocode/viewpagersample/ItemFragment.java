package com.feelfreetocode.viewpagersample;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class ItemFragment extends Fragment {

    private int color;
    public ItemFragment() {
        // Required empty public constructor
    }

    public static ItemFragment getObject(int color){
        ItemFragment itemFragment = new ItemFragment();
        itemFragment.color = color;
        return  itemFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_item, container, false);
        FrameLayout frameLayout = view.findViewById(R.id.layout);
        frameLayout.setBackgroundColor(getResources().getColor(color));

        return  view;
    }

}
