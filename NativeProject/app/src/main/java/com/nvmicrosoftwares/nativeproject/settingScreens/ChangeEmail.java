package com.nvmicrosoftwares.nativeproject.settingScreens;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nvmicrosoftwares.nativeproject.Database;
import com.nvmicrosoftwares.nativeproject.R;
public class ChangeEmail extends Fragment {

    EditText email;
    Button save;
    public ChangeEmail() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_email, container, false);
        email = v.findViewById(R.id.email);
        save = v.findViewById(R.id.saveemail);

        email.setText(Database.getEmail(getActivity().getApplicationContext()));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().trim().isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT).show();
                    return ;
                }

                Database.saveEmail(getActivity().getApplicationContext() , email.getText().toString().trim());
            }
        });

        return v;
    }


}
