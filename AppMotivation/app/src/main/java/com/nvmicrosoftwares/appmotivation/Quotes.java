package com.nvmicrosoftwares.appmotivation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class Quotes extends Fragment {
    MyQuote quote;
    int id ;
    public Quotes(){

    }

    public void setQuote(MyQuote quote , int id) {
        this.quote = quote; this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_quotes , null);

        TextView q = v.findViewById(R.id.quote);
        q.setText(quote.getText());

        TextView qq = v.findViewById(R.id.sno);
        qq.setText((id+1)+"");

        TextView from = v.findViewById(R.id.auther);

        CardView card = v.findViewById(R.id.card);
        from.setText(quote.getFrom());
        switch (id%5){
            case 1:
                card.setCardBackgroundColor(getResources().getColor(R.color.color1));
                return v;
            case 2:
                card.setCardBackgroundColor(getResources().getColor(R.color.color2));
                return v;
            case 3:
                card.setCardBackgroundColor(getResources().getColor(R.color.color3));
                return v;
            case 4:
                card.setCardBackgroundColor(getResources().getColor(R.color.color4));
                return v;

        }
        return  v;

    }
}
