package com.nvmicrosoftwares.nativeproject.settingScreens;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.nvmicrosoftwares.nativeproject.Database;
import com.nvmicrosoftwares.nativeproject.R;
import com.nvmicrosoftwares.nativeproject.models.MessageTypes;

public class UrgentNotificationSettings extends Fragment {

    CheckBox vibrate , sound;
    public UrgentNotificationSettings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_urgent_notification_settings, container, false);
        initAll(v);
        return v;
    }

    private void initAll(View v){
        vibrate = v.findViewById(R.id.vibrate);
        sound = v.findViewById(R.id.music);

        sound.setChecked(Boolean.valueOf(Database.getMessageById(getActivity().getApplicationContext() ,
                MessageTypes.PlayMusicOnly.getId())));
        vibrate.setChecked(Boolean.valueOf(Database.getMessageById(getActivity().getApplicationContext() ,
                MessageTypes.VibrateOnly.getId())));

        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Database.updateMessage(getActivity().getApplicationContext() ,
                                            String.valueOf(isChecked) ,
                                            MessageTypes.PlayMusicOnly.getId());
            }
        });

        vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Database.updateMessage(getActivity().getApplicationContext() ,
                        String.valueOf(isChecked) ,
                        MessageTypes.VibrateOnly.getId());
            }
        });
    }
}
