package com.nvmicrosoftwares.nativeproject.settingScreens;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Selection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nvmicrosoftwares.nativeproject.Database;
import com.nvmicrosoftwares.nativeproject.R;
import com.nvmicrosoftwares.nativeproject.models.MessageTypes;

public class UpdatMessageText extends Fragment{

    Button save;
    public UpdatMessageText() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_updat_message_text, container, false);
        ((EditText)v.findViewById(R.id.drivingMessage)).setText(
                                                        Database.getMessageById(getActivity().getApplicationContext(),
                                                        MessageTypes.Driving.getId()));

       final EditText t = ((EditText)v.findViewById(R.id.drivingMessage));
        t.setSelection(t.getText().length());



        save = v.findViewById(R.id.saveMessage);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database.updateMessage(getActivity().getApplicationContext() , t.getText().toString() , MessageTypes.Driving.getId());
                t.setText(Database.getMessageById(getActivity().getApplicationContext() , MessageTypes.Driving.getId()));
            }
        });
        return v;
    }
}
