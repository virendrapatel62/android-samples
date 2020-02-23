package com.feelfreetocode.kidszone;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class AlphabatesFragment extends Fragment {

    private  int images;
    private String dsc;
    static TextToSpeech tts ;
    public AlphabatesFragment() {
        // Required empty public constructor
    }

    public static AlphabatesFragment newInstance(String disc , int image , Context context) {

        AlphabatesFragment fragment = new AlphabatesFragment();
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        });

        fragment.dsc = disc;
        fragment.images = image;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_alphabates, container, false);
        ImageView image = view.findViewById(R.id.pic);
        final TextView desc = view.findViewById(R.id.desc);

        desc.setText(this.dsc);
        image.setImageResource(images);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(AlphabatesFragment.this.dsc , TextToSpeech.QUEUE_FLUSH , null);
            }
        });


        return view;
    }


}
